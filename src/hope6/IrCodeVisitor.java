import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;

public class IrCodeVisitor implements HOPE6Visitor {
   private int tmpCounter = 1;
   private int labelCounter = 1;
   String scope = "global";
   private SymbolTable symbolTable = new SymbolTable();
   Map<String, String> registerTypes = new HashMap<String, String>();

   private String getTmp() {
      String tmp = "%.t" + tmpCounter;
      tmpCounter++;
      return tmp;
   }

   private String getLabel() {
      String label = "label" + labelCounter;
      labelCounter++;
      return label;
   }

   static ArrayList<DeclaredStrings> generateStrings(SimpleNode root, Object data) {
      StringVisitor sv = new StringVisitor ();
      root.jjtAccept (sv, data);
      return ((Context) data).strings;
   }

   static void beginIr(SimpleNode root, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      ArrayList<DeclaredStrings> strings;
      DeclaredStrings s;
      String command;
      int strLen;

      try {
         buffer.write("declare i32 @printf(i8*, ...) #1");
         buffer.newLine();
         buffer.write("declare i32 @puts(i8*)");
         buffer.newLine();
         buffer.write("@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
         buffer.newLine();
         buffer.newLine();

         strings = generateStrings(root, data);
         for(ListIterator li = strings.listIterator(); li.hasNext();) {
            s = (DeclaredStrings) li.next();
            strLen = s.value.length() - 1;
            command = "@." + s.var + " = constant [" + strLen + " x i8] c" + s.value.substring(0,s.value.length()-1) + "\\00\"";
            buffer.write(command);
            buffer.newLine();
         }
      }
      catch(IOException e) {
         System.out.println("Failed to write start of IR code to file");
         e.printStackTrace(System.out);
      }
      return;
   }

   static void endIr(BufferedWriter buffer) {
      try {
         buffer.write("ret i32 0");
         buffer.newLine();
         buffer.write("}");
         buffer.newLine();
      }
      catch(IOException e) {
         System.out.println("Failed to write end of IR code to file");
         e.printStackTrace(System.out);
      }
   }

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visiting SimpleNode");
   }

   public Object visit(ASTprogram node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      beginIr(node, data);
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTmain node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      try {
         buffer.write("define i32 @main ()");
         buffer.newLine();
         buffer.write("{");
         buffer.newLine();
      }
      catch(IOException e) {
         System.out.println("Failed to write end of IR code to file");
         e.printStackTrace(System.out);
      }
      scope = "main";
      node.jjtGetChild(0).jjtAccept(this, data); // statement_block
      endIr(buffer);
      return data;
   }

   public Object visit(ASTfunction_declarations node, Object data) {
      if(node.jjtGetNumChildren() != 0) {
         node.jjtGetChild(0).jjtAccept(this, data); // function
         node.jjtGetChild(1).jjtAccept(this, data); // function_declarations
      }
      return data;
   }

   public Object visit(ASTfunction node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data);
      symbolTable.insert(scope, id, type, "FUNC");
      scope = id;
      String paramsString = (String) node.jjtGetChild(2).jjtAccept(this, data);
      String mty = symbolTable.getSymbol("global", id);
      String signature = type + "(";
      String[] params = paramsString.split(",");

      int numParams;
      if(paramsString.equals("")) {
         numParams = 0;
      }
      else {
         numParams = params.length;
      }
      String[] paramsId = new String[numParams];
      String[] paramsType = new String[numParams];
      String[] paramsMty = new String[numParams];

      if(!paramsString.equals("")) {
         for(int i = 0; i < numParams; i++) {
            params[i] = params[i].trim();
            String[] param = params[i].split(" ");
            paramsType[i] = param[0];
            paramsId[i] = param[1];
            paramsMty[i] = symbolTable.getSymbol(id, paramsId[i]);
            signature += paramsType[i];
            if(i < params.length - 1) {
               signature += ", ";
            }
         }
      }
      else {
         signature += "void";
      }
      signature += ")";

      String command = "define " + mty + " @" + id + "(";

      for (int i = 0; i < numParams; i++) {
         command += " " + paramsMty[i] + " %." + paramsId[i];
         if(i < params.length - 1) {
            command += ",";
         }
      }
      command += ")\n{\nentry:";

      try {
         buffer.write(command);
         buffer.newLine();

         command = "%.p.return = alloca " + mty;
         buffer.write(command);
         buffer.newLine();

         if(!paramsString.equals("")) {
            for(int i = 0; i < params.length; i++) {
               command = "%.p." + paramsId[i] + " = alloca " + paramsMty[i];
               buffer.write(command);
               buffer.newLine();
               command = "store " + paramsMty[i] + " %." + paramsId[i] + ", " + paramsMty[i] + "* %.p." + paramsId[i];
               buffer.write(command);
               buffer.newLine();
            }
         }
         node.jjtGetChild(3).jjtAccept(this, data); // statement_block (function body)

         String expr = (String) node.jjtGetChild(4).jjtAccept(this, data);
         command = "store " + mty + " " + expr + ", " + mty + "* %.p.return\n";
         buffer.write(command);
         buffer.newLine();

         String t = getTmp();
         command = t + " = add i1 0,0\nbr label %exit\n"; // add nop before exit label
         buffer.write(command);

         buffer.write("exit:\n");
         String tmp = getTmp();
         command = tmp + " = load " + mty + ", " + mty + "* %.p.return";
         registerTypes.put(tmp, mty);
         buffer.write(command);
         buffer.newLine();
         command = "ret " + mty + " " + tmp;
         buffer.write(command);
         buffer.newLine();
         buffer.write("}\n");
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for function to file");
         e.printStackTrace(System.out);
      }
      return data;
   }

   public Object visit(ASTstatement_block node, Object data) {
      if(node.jjtGetNumChildren() != 0) {
         node.jjtGetChild(0).jjtAccept(this, data); //statement
         node.jjtGetChild(1).jjtAccept(this, data); //statement_block
      }
      return data;
   }

   public Object visit(ASTstatement node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTexpression node, Object data) {
      String result = "";
      if(node.jjtGetNumChildren() == 1) {
         result = (String) node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         for(int i = 0; (i + 2) < node.jjtGetNumChildren(); i += 2) {
            Context context = (Context) data;
            BufferedWriter buffer = context.buffer;
            String command = "";
            String prevResult = "";

            if(i != 0) {
               prevResult = result;
            }

            result = getTmp();
            String arg1;
            if(i == 0) {
               arg1 = (String) node.jjtGetChild(i).jjtAccept(this, data);
            }
            else {
               arg1 = prevResult;
            }
            String op = (String) node.jjtGetChild(i + 1).jjtAccept(this, data);
            String arg2 = (String) node.jjtGetChild(i + 2).jjtAccept(this, data);

            if(op.equals("+")) {
               registerTypes.put(result, "i32");
               command = result + " = add i32 " + arg1 + ", " + arg2;
            }
            else if(op.equals("-")) {
               registerTypes.put(result, "i32");
               command = result + " = sub i32 " + arg1 + ", " + arg2;
            }
            else if(op.equals("*")) {
               registerTypes.put(result, "i32");
               command = result + " = mul i32 " + arg1 + ", " + arg2;
            }
            else if(op.equals("/")) {
               registerTypes.put(result, "i32");
               command = result + " = sdiv i32 " + arg1 + ", " + arg2;
            }
            else if(op.equals("%")) {
               registerTypes.put(result, "i32");
               command = result + " = urem i32 " + arg1 + ", " + arg2;
            }
            else if(op.equals("&")) {
               registerTypes.put(result, "i1");
               command = result + " = and i32 " + arg1 + ", " + arg2;
            }
            else {
               registerTypes.put(result, "i1");
               command = result + " = or i32 " + arg1 + ", " + arg2;
            }

            try {
               buffer.write(command);
               buffer.newLine();
            }
            catch (IOException e) {
               System.out.println("Failed to write IR code of expression to file");
               e.printStackTrace(System.out);
            }
         }
      }
      return result;
   }

   public Object visit(ASTcondition node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String arg1 = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String op = (String) node.jjtGetChild(1).jjtAccept(this, data);
      String arg2 = (String) node.jjtGetChild(2).jjtAccept(this, data);
      String tmp = getTmp();

      String type = registerTypes.get(arg1);
      if(type == null) {
         type = "i32";
      }

      String symOp;
      if(op.equals("==")) {
         symOp = "eq";
      }
      else if(op.equals("!=")) {
         symOp = "neq";
      }
      else if(op.equals(">")) {
         symOp = "sgt";
      }
      else if(op.equals(">=")) {
         symOp = "sge";
      }
      else if(op.equals("<")) {
         symOp = "slt";
      }
      else {
         symOp = "sle";
      }

      String command = tmp + " = icmp " + symOp + " " + type + " " + arg1 + ", " + arg2;
      try {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for condition to file");
         e.printStackTrace(System.out);
      }
      return tmp;
   }

   public Object visit(ASTfragment node, Object data) {
      String frag = (String) node.jjtGetChild(0).jjtAccept(this, data);
      return frag;
   }

   public Object visit(ASTfunction_call node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String tmp = getTmp();
      String id = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String args = (String) node.jjtGetChild(1).jjtAccept(this, data);
      String funcType = symbolTable.getSymbol("global", id);
      String prevScope = scope;
      scope = id;

      String command = tmp + " = call " + funcType + " @" + id + " (" + args + ")";
      registerTypes.put(tmp, funcType);

      try {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for a function call to file");
         e.printStackTrace (System.out);
      }
      scope = prevScope;
      return tmp;
   }

   public Object visit (ASTinteger node, Object data) {
      return node.value;
   }

   public Object visit (ASTstring node, Object data) {
      return node.value;
   }

   public Object visit (ASTbool node, Object data) {
      return node.value;
   }

   public Object visit (ASTassignment node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String id = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String expr = (String) node.jjtGetChild(1).jjtAccept(this, data);
      String type = symbolTable.getSymbol(scope, id);
      String tmp, var, command;
      DeclaredStrings s;
      int length;

      if(type.equals ("i8*")) {
         ListIterator li = context.strings.listIterator();
         var = "";
         while(li.hasNext()) {
            s = (DeclaredStrings) li.next();
            if (s.value.equals(expr)) {
               var = s.var;
               break;
            }
         }

         length = expr.length() - 1;
         tmp = getTmp();
         command = tmp + " = getelementptr [" + length + " x i8], [" + length + " x i8]* @." + var + ", i64 0, i64 0\n";
         command = command + "store i8* " + tmp + ", i8** %.p." + id;
      }
      else {
         command = "store " + type + " " + expr + ", " + type + "* %.p." + id;
      }

      try {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for an assignment to file. Exception: ");
         e.printStackTrace (System.out);
      }
      return data;
   }

   public Object visit (ASTdeclaration node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data);
      symbolTable.insert(scope, id, type, "VAR");

      String mty;
      if(type.equals("int")) {
         mty = "i32";
      }
      else if(type.equals("bool")) {
         mty = "i1";
      }
      else {
         mty = "i8*";
      }
      String command = "%.p." + id + " = alloca " + mty;

      try {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code of declaration to file");
         e.printStackTrace(System.out);
      }
      return data;
   }

   public Object visit (ASTprint node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      DeclaredStrings s;
      String var, command, tmp;
      int length;

      String result = (String) node.jjtGetChild(0).jjtAccept(this, data);
      if(result.charAt(0) == '"') {
         ListIterator li = context.strings.listIterator();
         var = "";
         while(li.hasNext()) {
            s = (DeclaredStrings) li.next();
            if(s.value.equals(result)) {
               var = s.var;
               break;
            }
         }

         length = result.length() - 1;
         tmp = getTmp();
         command = tmp + " = getelementptr [" + length + " x i8], [" + length + " x i8]* @." + var + ", i64 0, i64 0\n";
         result = tmp;
         command = command + "call i32 @puts (i8* ";
      }
      else {
         if(registerTypes.get(result).equals("i8*")) {
            command = "call i32 @puts(i8* ";
         }
         else {
            command = "call i32" + " (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, ";
            command = command + registerTypes.get(result) + " 0, " + registerTypes.get(result) + " 0), " + registerTypes.get(result) + " ";
         }
      }

      try {
         buffer.write(command);
         buffer.write(result);
         buffer.write(")");
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for print statement to file");
         e.printStackTrace (System.out);
      }
      return data;
   }

   public Object visit(ASTif_statement node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String trueBlock = getLabel();
      String falseBlock = getLabel();
      String endBlock = getLabel();

      String condition = (String) node.jjtGetChild (0).jjtAccept(this, data);
      String command = "br i1 " + condition + ", label %" + trueBlock + ", label %" + falseBlock;
      try {
         buffer.write(command);
         buffer.newLine();

         buffer.write(trueBlock + ":");
         buffer.newLine();
         node.jjtGetChild(1).jjtAccept(this, data);
         buffer.write("br label %" + endBlock);
         buffer.newLine();

         buffer.write(falseBlock + ":");
         buffer.newLine();
         node.jjtGetChild(2).jjtAccept(this, data);
         buffer.write("br label %" + endBlock);
         buffer.newLine();

         buffer.write(endBlock + ":");
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for if statement to file");
         e.printStackTrace(System.out);
      }
      return data;
   }

   public Object visit (ASTwhile_loop node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String conditionLabel = getLabel();
      String trueBlock = getLabel();
      String endBlock = getLabel();

      String condition, command;
      try {
         buffer.write("br label %" + conditionLabel + "\n" + conditionLabel + ": ");
         condition = (String) node.jjtGetChild (0).jjtAccept(this, data);
         command = "br i1 " + condition + ", label %" + trueBlock + ", label %" + endBlock;
         buffer.write(command);
         buffer.newLine();

         buffer.write(trueBlock + ":");
         buffer.newLine();
         node.jjtGetChild(1).jjtAccept(this, data);

         buffer.write("br label %" + conditionLabel);
         buffer.newLine();
         buffer.write(endBlock + ":");
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for while to file");
         e.printStackTrace(System.out);
      }
      return data;
   }

   public Object visit (ASTlhs_identifier node, Object data) {
      return node.value;
   }

   public Object visit (ASTrhs_identifier node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;

      String id = (String) node.value;
      String type;

      if(symbolTable.getDeclType(id).equals("FUNC")) {
         return id;
      }
      else {
         type = symbolTable.getSymbol(scope, id);
      }
      String tmp = getTmp();
      String command = tmp + " = " + "load " + type + ", " + type + "* %.p." + id;
      registerTypes.put(tmp,type);

      try {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code for RHS identifier to file");
         e.printStackTrace (System.out);
      }
      return tmp;
   }

   public Object visit (ASTtype node, Object data) {
      return node.value;
   }

   public Object visit(ASTparameter_list node, Object data) {
      String params = "";
      if(node.jjtGetNumChildren() != 0) {
         params = (String) node.jjtGetChild(0).jjtAccept(this, data); // parameter
         if(node.jjtGetNumChildren() != 1) {
            params += ", " + (String) node.jjtGetChild(1).jjtAccept(this, data); // parameter_list
         }
      }
      return params;
   }

   public Object visit(ASTparameter node, Object data) {
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data);
      symbolTable.insert(scope, id, type, "PARAM");
      String finalParam = type + " " + id;
      return finalParam;
   }

   public Object visit(ASTargument_list node, Object data) {
      String args = "";
      if(node.jjtGetNumChildren() != 0) {
         args = (String) node.jjtGetChild(0).jjtAccept(this, data); // argument
         if(node.jjtGetNumChildren() != 1) {
            args += ", " + (String) node.jjtGetChild(1).jjtAccept(this, data); // argument_list
         }
      }
      return args;
   }

   public Object visit(ASTargument node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      String type, var;
      DeclaredStrings s;
      String arg = (String) node.jjtGetChild(0).jjtAccept(this, data);

      if(arg.matches("-?\\d+")) {
         type = "i32";
      }
      else if(arg.charAt(0) == '"') {
         ListIterator li = context.strings.listIterator ();
         var = "";
         while(li.hasNext()) {
            s = (DeclaredStrings) li.next();
            if(s.value.equals(arg)) {
               var = s.var;
               break;
            }
         }

         int length = arg.length() - 1;
         String tmp = getTmp();
         registerTypes.put(tmp, "i8*");
         String command = tmp + " = getelementptr [" + length + " x i8], [" + length + " x i8]* @." + var + ", i64 0, i64 0";
         arg = tmp;
         type = "i8*";
         try {
            buffer.write(command);
            buffer.newLine();
         }
         catch (IOException e) {
            System.out.println("Failed to write IR code for RHS identifier to file");
            e.printStackTrace(System.out);
         }
      }
      else {
         type = registerTypes.get(arg);
      }

      String finalArg = type + " " + arg;
      return finalArg;
   }

   public Object visit(ASTarith_op node, Object data) {
      return node.value;
   }

   public Object visit(ASTlogic_op node, Object data) {
      return node.value;
   }

   public Object visit(ASTcomp_op node, Object data) {
      return node.value;
   }
}

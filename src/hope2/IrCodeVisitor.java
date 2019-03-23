import java.io.BufferedWriter;
import java.io.IOException;

public class IrCodeVisitor implements HOPE2Visitor {

   private int tmpCounter = 1;
   private SymbolTable symbolTable = new SymbolTable();

   private String getTmp() { // makes labels for loading and writing varibles
      String tmp = "%.t" + tmpCounter;
      tmpCounter++;
      return tmp;
   }

   static void beginIr(BufferedWriter buffer) { // declares functions and starts file
      try {
         buffer.write("declare i32 @printf(i8*, ...) #1");
         buffer.newLine();
         buffer.write("@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
         buffer.newLine();
         buffer.write("define i32 @main ()");
         buffer.newLine();
         buffer.write("{");
         buffer.newLine();
      }
      catch(IOException e) {
         System.out.println("Failed to write start of IR code to file");
         e.printStackTrace(System.out);
      }
      return;
   }

   static void endIr(BufferedWriter buffer) { // final return statement of file
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

      beginIr(buffer);
      node.jjtGetChild(0).jjtAccept(this, data); // statement_block
      endIr(buffer);
      return data;
   }

   public Object visit (ASTstatement_block node, Object data) {
      if (node.jjtGetNumChildren() != 0) {
         node.jjtGetChild(0).jjtAccept(this, data); //statement
         node.jjtGetChild(1).jjtAccept(this, data); //statement_block
      }
      return data;
   }

   public Object visit(ASTstatement node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // print or assignment or declaration
      return data;
   }

   public Object visit(ASTexpression node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      String command = null;
      String result;

      if (node.jjtGetNumChildren() == 1) {
         result = (String) node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         result = getTmp();
         String arg1 = (String) node.jjtGetChild(0).jjtAccept(this, data); // fragment
         String op = (String) node.jjtGetChild(1).jjtAccept(this, data); // op
         String arg2 = (String) node.jjtGetChild(2).jjtAccept(this, data); // fragment

         if (op.equals("+")) {
            command = result + " = add i32 " + arg1 + ", " + arg2;
         }
         else if (op.equals("-")) {
            command = result + " = sub i32 " + arg1 + ", " + arg2;
         }
         else if (op.equals("*")) {
            command = result + " = mul i32 " + arg1 + ", " + arg2;
         }
         else {
            command = result + " = sdiv i32 " + arg1 + ", " + arg2;
         }
      }

      try {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code of expression to file");
         e.printStackTrace(System.out);
      }
      return result;
   }

   public Object visit(ASTfragment node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // integer or identifier
      return data;
   }

   public Object visit (ASTinteger node, Object data) {
      return node.value;
   }

   public Object visit (ASTassignment node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      String id = (String) node.jjtGetChild(0).jjtAccept(this, data); // identifier
      String expr = (String) node.jjtGetChild(1).jjtAccept(this, data); // expression
      String type = "i32"; // only integers in this language
      String result = "store " + type + " " + expr + ", " + type + "* %.p." + id;

      try {
         buffer.write(result);
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code of assignment to file");
         e.printStackTrace(System.out);
      }
      return data;
   }

   public Object visit (ASTdeclaration node, Object data) {
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data); // type
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data); //identifier
      String mty = "i32"; // only integers in this language
      String command = "%.p." + id + " = alloca " + mty;
      symbolTable.insert(id); // register variable

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
      String result = (String) node.jjtGetChild(0).jjtAccept(this, data); // expression
      try {
         buffer.write("call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ");
         buffer.write(result);
         buffer.write(")");
         buffer.newLine();
      }
      catch (IOException e) {
         System.out.println("Failed to write IR code of print to file");
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

      String id = (String) node.value; // identifier
      String type = "i32"; // only integers in this language
      String tmp = getTmp();
      String command = tmp + " = " + "load " + type + ", " + type + "* %.p." + id;

      try
      {
         buffer.write(command);
         buffer.newLine();
      }
      catch (IOException e)
      {
         System.out.println ("Failed to write IR code for RHS identifier to file");
         e.printStackTrace (System.out);
      }

      return tmp;
   }

   public Object visit (ASTtype node, Object data) {
      return node.value;
   }

   public Object visit(ASTbinary_arith_op node, Object data) {
      return node.value;
   }

}

// import java.io.BufferedWriter;
// import java.io.IOException;
// import java.util.Map;
// import java.util.HashMap;
// import java.util.ArrayList;
// import java.util.ListIterator;
//
// public class IrCodeVisitor implements HOPE3Visitor {
//
//    private int tmpCounter = 1;
//    private SymbolTable symbolTable = new SymbolTable();
//    private Map<String, String> registerTypes = new HashMap<String, String>();
//
//    private String getTmp() {
//       String tmp = "%.t" + tmpCounter;
//       tmpCounter++;
//       return tmp;
//    }
//
//    static ArrayList<DeclaredStrings> generateStrings(SimpleNode root, Object data) {
//       StringVisitor sv = new StringVisitor();
//       root.jjtAccept(sv, data);
//       return(((Context)data).strings);
//    }
//
//    static void beginIr(BufferedWriter buffer) {
//       Context context = (Context) data;
//       BufferedWriter buffer = context.buffer;
//       ArrayList<DeclaredStrings> strings;
//       DeclaredStrings str;
//       String command;
//       int str_len;
//       try {
//          buffer.write("declare i32 @printf(i8*, ...) #1");
//          buffer.newLine();
//          buffer.write("declare i32 @puts(i8*)");
//          buffer.newLine();
//          buffer.write("@.1arg_str = private unnamed_addr constant [4 x i8] c\"%d\\0A\\00\", align 1");
//          buffer.newLine();
//          buffer.newLine();
//
//          strings = generateStrings(root, data);
//          for(ListIterator li = strings.listIterator(); li.hasNext();) {
//             s = (DeclaredStrings) li.next();
//             str_len = s.value.length() - 1;
//             command = "@." + s.var + " = constant [" + str_len + " x i8] c" + s.value.substring(0, s.value.length() - 1) + "\\00\"";
//             buffer.write(command);
//             buffer.newLine();
//          }
//
//          buffer.newLine();
//          buffer.write("define i32 @main ()");
//          buffer.newLine();
//          buffer.write("{");
//          buffer.newLine();
//       }
//       catch(IOException e) {
//          System.out.println("Failed to write start of IR code to file");
//          e.printStackTrace(System.out);
//       }
//       return;
//    }
//
//    static void endIr(BufferedWriter buffer) {
//       try {
//          buffer.write("ret i32 0");
//          buffer.newLine();
//          buffer.write("}");
//          buffer.newLine();
//       }
//       catch(IOException e) {
//          System.out.println("Failed to write end of IR code to file");
//          e.printStackTrace(System.out);
//       }
//    }
//
//    public Object visit(SimpleNode node, Object data) {
//       throw new RuntimeException("Visiting SimpleNode");
//    }
//
//    public Object visit(ASTprogram node, Object data) {
//       Context context = (Context) data;
//       BufferedWriter buffer = context.buffer;
//
//       beginIr(node, data);
//       node.jjtGetChild(0).jjtAccept(this, data);
//       endIr(buffer);
//       return data;
//    }
//
//    public Object visit (ASTstatement_block node, Object data) {
//       if (node.jjtGetNumChildren() != 0) {
//          node.jjtGetChild(0).jjtAccept(this, data); //statement
//          node.jjtGetChild(1).jjtAccept(this, data); //statement_block
//       }
//       return data;
//    }
//
//    public Object visit(ASTstatement node, Object data) {
//       node.jjtGetChild(0).jjtAccept(this, data);
//       return data;
//    }
//
//    public Object visit(ASTexpression node, Object data) {
//       Context context = (Context) data;
//       BufferedWriter buffer = context.buffer;
//       DeclaredStrings str;
//       String command = null;
//       String var, tmp;
//
//       if (node.jjtGetNumChildren() == 1) {
//          result = (String) node.jjtGetChild(0).jjtAccept(this, data);
//       }
//       else {
//          result = getTmp();
//          String arg1 = (String) node.jjtGetChild(0).jjtAccept(this, data);
//          String arg2 = (String) node.jjtGetChild(2).jjtAccept(this, data);
//
//          if ((String) node.jjtGetChild(1).jjtAccept(this, data) == "+") {
//             command = result + " = add i32 " + arg1 + ", " + arg2;
//          }
//          else if ((String) node.jjtGetChild(1).jjtAccept(this, data) == "-") {
//             command = result + " = sub i32 " + arg1 + ", " + arg2;
//          }
//          else if ((String) node.jjtGetChild(1).jjtAccept(this, data) == "*") {
//             command = result + " = sub i32 " + arg1 + ", " + arg2;
//          }
//          else {
//             command = result + " = sdiv i32 " + arg1 + ", " + arg2;
//          }
//       }
//
//       try {
//          buffer.write(command);
//          buffer.newLine();
//       }
//       catch (IOException e) {
//          System.out.println("Failed to write IR code of expression to file");
//          e.printStackTrace(System.out);
//       }
//       return result;
//    }
//
//    public Object visit(ASTfragment node, Object data) {
//       node.jjtGetChild(0).jjtAccept(this, data);
//       return data;
//    }
//
//    public Object visit (ASTinteger node, Object data) {
//       return node.value;
//    }
//
//    public Object visit (ASTassignment node, Object data) {
//       Context context = (Context) data;
//       BufferedWriter buffer = context.buffer;
//       String id = (String) node.jjtGetChild(0).jjtAccept(this, data);
//       String expr = (String) node.jjtGetChild(1).jjtAccept(this, data);
//       String type = "int";
//       String result = "store " + type + " " + expr + ", " + type + "* %.p." + id;
//
//       try {
//          buffer.write(result);
//          buffer.newLine();
//       }
//       catch (IOException e) {
//          System.out.println("Failed to write IR code of assignment to file");
//          e.printStackTrace(System.out);
//       }
//       return data;
//    }
//
//    public Object visit (ASTdeclaration node, Object data) {
//       Context context = (Context) data;
//       BufferedWriter buffer = context.buffer;
//       String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
//       String id = (String) node.jjtGetChild(1).jjtAccept(this, data);
//       String mty = "i32";
//       String command = "%.p." + id + " = alloca " + mty;
//       symbolTable.insert(id);
//
//       try {
//          buffer.write(command);
//          buffer.newLine();
//       }
//       catch (IOException e) {
//          System.out.println("Failed to write IR code of declaration to file");
//          e.printStackTrace(System.out);
//       }
//       return data;
//    }
//
//    public Object visit (ASTprint node, Object data) {
//       Context context = (Context) data;
//       BufferedWriter buffer = context.buffer;
//       String result = (String) node.jjtGetChild(0).jjtAccept(this, data);
//       try {
//          buffer.write("call i32 (i8*, ...) @printf (i8* getelementptr inbounds ([4 x i8], [4 x i8]* @.1arg_str, i32 0, i32 0), i32 ");
//          buffer.write(result);
//          buffer.write(")");
//          buffer.newLine();
//       }
//       catch (IOException e) {
//          System.out.println("Failed to write IR code of print to file");
//          e.printStackTrace(System.out);
//       }
//       return data;
//    }
//
//    public Object visit (ASTidentifier node, Object data) {
//       return node.value;
//    }
//
//    public Object visit (ASTtype node, Object data) {
//       return node.value;
//    }
//
//    public Object visit (ASTbinary_op node, Object data) {
//       node.jjtGetChild(0).jjtAccept(this, data);
//       return data;
//    }
//
//    public Object visit (ASTbinary_arith_op node, Object data) {
//       return node.value;
//    }
//
//    public Object visit (ASTbinary_logic_op node, Object data) {
//       return node.value;
//    }
//
// }

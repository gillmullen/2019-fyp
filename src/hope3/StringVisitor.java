import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;

public class StringVisitor implements HOPE3Visitor {

   SymbolTable symbolTable = new SymbolTable();

   public Boolean containsString (ArrayList<DeclaredStrings> strs, String t){
      DeclaredStrings ds;
      ListIterator li = strs.listIterator();
      while (li.hasNext()) {
         ds = (DeclaredStrings) li.next();
         if (ds.var.equals(t)) {
            return true;
         }
      }
      return false;
   }

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visit SimpleNode");
   }

   public Object visit(ASTprogram node, Object data) {
      node.jjtGetChild(0).jjtAccept (this, data); /// statement_block
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
      node.jjtGetChild(0).jjtAccept(this, data); // print or assignment or declaration
      return data;
   }

   public Object visit(ASTexpression node, Object data) {
      if(node.jjtGetNumChildren() == 1) {
         return (String) node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         return "";
      }
   }

   public Object visit(ASTinteger node, Object data) {
      return node.value;
   }

   public Object visit(ASTstring node, Object data) {
      return node.value;
   }

   public Object visit(ASTbool node, Object data) {
      return node.value;
   }

   public Object visit(ASTassignment node, Object data) {
      int offset;
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      ArrayList<DeclaredStrings> strings = context.strings;

      String id = (String) node.jjtGetChild(0).jjtAccept(this, data); // identifier
      String type = symbolTable.getSymbol(id);

      if(type.equals("i8*")) {
         id = id + ".0";
         while(containsString(strings, id)) {
            offset = id.lastIndexOf(".");
            id = id.substring(0, offset + 1) +
            Integer.toString((Integer.parseInt(id.substring(offset+1,id.length())))+1,10);
         }
         String expr = (String) node.jjtGetChild(1).jjtAccept(this, data); // expression
         strings.add(new DeclaredStrings(id,expr));
      }

      return data;
   }

   public Object visit(ASTdeclaration node, Object data) {
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data); // type
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data); // identifier
      symbolTable.insert(id, type);
      return data;
   }

   public Object visit(ASTprint node, Object data) {
      return data;
   }

   public Object visit(ASTlhs_identifier node, Object data) {
      return node.value;
   }

   public Object visit(ASTrhs_identifier node, Object data) {
      return node.value;
   }

   public Object visit(ASTtype node, Object data) {
      return node.value;
   }

   public Object visit(ASTbinary_arith_op node, Object data) {
      return node.value;
   }

   public Object visit(ASTbinary_logic_op node, Object data) {
      return node.value;
   }
}

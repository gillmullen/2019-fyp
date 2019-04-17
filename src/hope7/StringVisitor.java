import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.ListIterator;

public class StringVisitor implements HOPE7Visitor {

   SymbolTable symbolTable = new SymbolTable();
   String scope = "global";

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
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTmain node, Object data) {
      scope = "main";
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTfunction_declarations node, Object data) {
      if(node.jjtGetNumChildren() != 0) {
         node.childrenAccept(this, data);
      }
      return data;
   }

   public Object visit(ASTfunction node, Object data) {
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data);
      symbolTable.insert(scope, id, type, "FUNC");
      scope = id;
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTstatement_block node, Object data) {
      if(node.jjtGetNumChildren() != 0) {
         node.childrenAccept(this, data);
      }
      return data;
   }

   public Object visit(ASTstatement node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTexpression node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTcondition node, Object data) {
      if(node.jjtGetNumChildren() == 1) {
         return (String) node.jjtGetChild(0).jjtAccept(this, data);
      }
      else {
         return "";
      }
   }

   public Object visit(ASTfragment node, Object data) {
      return (String) node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit(ASTfunction_call node, Object data) {
      String id = (String) node.jjtGetChild(0).jjtAccept(this, data);
      scope = id;
      node.childrenAccept(this, data);
      return data;
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

   public Object visit(ASTarray node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTarray_index node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTindex node, Object data) {
      return node.value;
   }

   public Object visit(ASTassignment node, Object data) {
      int offset;
      Context context = (Context) data;
      BufferedWriter buffer = context.buffer;
      ArrayList<DeclaredStrings> strings = context.strings;

      String id = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String type = symbolTable.getSymbol(scope, id);

      if(type.equals("i8*")) {
         id = id + ".0";
         while(containsString(strings, id)) {
            offset = id.lastIndexOf(".");
            id = id.substring(0, offset + 1) +
            Integer.toString((Integer.parseInt(id.substring(offset+1,id.length())))+1,10);
         }
      }
      return data;
   }

   public Object visit(ASTdeclaration node, Object data) {
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data);
      symbolTable.insert(scope, id, type, "VAR");
      return data;
   }

   public Object visit(ASTarray_declaration node, Object data) {
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data);
      String size = (String) node.jjtGetChild(1).jjtAccept(this, data);
      String id = (String) node.jjtGetChild(2).jjtAccept(this, data);
      symbolTable.insertArray(scope, id, type, "VAR", size);
      return data;
   }

   public Object visit(ASTarray_size node, Object data) {
      return node.value;
   }

   public Object visit(ASTprint node, Object data) {
      return data;
   }

   public Object visit(ASTif_statement node, Object data) {
      return data;
   }

   public Object visit(ASTwhile_loop node, Object data) {
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

   public Object visit(ASTparameter_list node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTparameter node, Object data) {
      String type = (String) node.jjtGetChild(0).jjtAccept(this, data); // type
      String id = (String) node.jjtGetChild(1).jjtAccept(this, data); // identifier
      symbolTable.insert(scope, id, type, "PARAM");
      return data;
   }

   public Object visit(ASTargument_list node, Object data) {
      node.childrenAccept(this, data);
      return data;
   }

   public Object visit(ASTargument node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTbinary_arith_op node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTarith_op node, Object data) {
      return node.value;
   }

   public Object visit(ASTbinary_logic_op node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTlogic_op node, Object data) {
      return node.value;
   }

   public Object visit(ASTcomp_op node, Object data) {
      return node.value;
   }
}

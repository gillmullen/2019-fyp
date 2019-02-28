import java.util.*;

public class SemanticVisitor implements HOPE2Visitor {

   private SymbolTable st;
   private LinkedList<String> variablesRead;
   private LinkedList<String> variablesWritten;

   private Boolean allVariablesWrittenTo = true;
   private Boolean allVariablesReadFrom = true;
   private Boolean declaredBeforeUse = true;

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visit SimpleNode");
   }

   public Object visit (ASTprogram node, Object data) {
      st = (SymbolTable) data;
      variablesRead = st.getVariables();
      variablesWritten = st.getVariables();

      node.childrenAccept(this, data); // visit all children of program node

      if (!variablesWritten.isEmpty()) {
         allVariablesWrittenTo = false;
      }
      if (!variablesRead.isEmpty()) {
         allVariablesReadFrom = false;
      }

      if (allVariablesReadFrom) {
         System.out.println("Pass: All Variables Read From!");
      }
      else {
         System.out.println("Fail: Variable(s) " + variablesRead + " Not Read From!");
      }
      if (allVariablesWrittenTo) {
         System.out.println("Pass: All Variables Written To!");
      }
      else {
         System.out.println("Fail: Variable(s) " + variablesWritten + " Not Read From!");
      }
      if (declaredBeforeUse) {
         System.out.println("Pass: All Variables and Functions Declared Before Use!");
      }
      return DataType.Program;
   }

   public Object visit (ASTstatement_block node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTstatement node, Object data) {
      return (node.jjtGetChild(0).jjtAccept(this, data));
   }

   public Object visit (ASTexpression node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTfragment node, Object data) {
      return (node.jjtGetChild(0).jjtAccept(this, data));
   }

   public Object visit (ASTinteger node, Object data) {
      return DataType.Integer;
   }

   public Object visit (ASTassignment node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTdeclaration node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTprint node, Object data) {
      return (node.jjtGetChild(0).jjtAccept(this, data));
   }

   public Object visit (ASTidentifier node, Object data) {
      SimpleNode parent = (SimpleNode) node.jjtGetParent();
      String type = parent.toString();
      String value = (String) node.jjtGetValue();

      if(type != "declaration") {
         if(st.lookup(value)) {
            variablesRead.remove(value);
         }
         else {
            declaredBeforeUse = false;
            System.out.println("Fail: " + value + " Not Declared Before Use!");
            return DataType.TypeUnknown;
         }
      }

      return DataType.Integer;
   }

   public Object visit (ASTtype node, Object data) {
      return DataType.Integer;
   }

   public Object visit (ASTbinary_arith_op node, Object data) {
      return DataType.Op;
   }

}

import java.util.*;

public class SemanticVisitor implements HOPE3Visitor {

   private SymbolTable st;
   private LinkedList<String> variablesRead;
   private LinkedList<String> variablesWritten;

   private Boolean allVariablesWrittenTo = true;
   private Boolean allVariablesReadFrom = true;
   private Boolean declaredBeforeUse = true;
   private Boolean existsDuplicateVar = false;

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
      existsDuplicateVar = st.existsDuplicate();

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
         System.out.println("Fail: Variable(s) " + variablesWritten + " Not Written To!");
      }
      if (declaredBeforeUse) {
         System.out.println("Pass: All Variables and Functions Declared Before Use!");
      }
      if(existsDuplicateVar) {
         System.out.println("Fail: Duplicate Variables Defined!");
      }
      else {
         System.out.println("Pass: Variables Declared Only Once!");
      }
      return DataType.Program;
   }

   public Object visit (ASTstatement_block node, Object data) {
      return node.childrenAccept(this, data); // statement and statement_block or none
   }

   public Object visit (ASTstatement node, Object data) {
      return (node.jjtGetChild(0).jjtAccept(this, data)); // print or assignment or declaration
   }

   public Object visit (ASTexpression node, Object data) {
      return node.childrenAccept(this, data); // fragment (and op and fragment)
   }

   public Object visit (ASTfragment node, Object data) {
      return (node.jjtGetChild(0).jjtAccept(this, data)); // id or int or str or bool
   }

   public Object visit (ASTinteger node, Object data) {
      return DataType.Integer;
   }

   public Object visit (ASTstring node, Object data) {
      return DataType.String;
   }

   public Object visit (ASTbool node, Object data) {
      return DataType.Boolean;
   }

   public Object visit (ASTassignment node, Object data) {
      return node.childrenAccept(this, data); //
   }

   public Object visit (ASTdeclaration node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTprint node, Object data) {
      return (node.jjtGetChild(0).jjtAccept(this, data));
   }

   public Object visit (ASTlhs_identifier node, Object data) {
      SimpleNode parent = (SimpleNode) node.jjtGetParent();
      String type = parent.toString();
      String value = (String) node.value;

      if(type.equals("assignment")) {
         if(!st.lookup(value)) {
            declaredBeforeUse = false;
            System.out.println("Fail: " + value + " Not Declared Before Use!");
         }
         variablesWritten.remove(value);
      }

      return DataType.Id;
   }

   public Object visit (ASTrhs_identifier node, Object data) {
      String value = (String) node.value;
      variablesRead.remove(value);
      return DataType.Id;
   }

   public Object visit (ASTtype node, Object data) {
      String type = (String) node.value;
      if(type.equals("int")) {
         return DataType.Integer;
      }
      else if(type.equals("boolean")) {
         return DataType.Boolean;
      }
      else if(type.equals("string")) {
         return DataType.String;
      }
      return DataType.TypeUnknown;
   }

   public Object visit (ASTbinary_arith_op node, Object data) {
      return DataType.ArithOp;
   }

   public Object visit (ASTbinary_logic_op node, Object data) {
      return DataType.LogicOp;
   }

}

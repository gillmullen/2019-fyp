import java.util.*;

public class SemanticVisitor implements HOPE7Visitor {

   private SymbolTable st;
   private LinkedList<String> variablesRead;
   private LinkedList<String> variablesWritten;
   private LinkedList<String> functions;
   private String scope = "global"; // always starting scope

   private Boolean allVariablesWrittenTo = true;
   private Boolean allVariablesReadFrom = true;
   private Boolean allFuncsCalled = true;
   private Boolean declaredBeforeUse = true;
   private Boolean existsDuplicateVar = false;
   private Boolean arithmeticOnIntegers = true;
   private Boolean logicOnBooleans = true;
   private Boolean correctAssignmentType = true;

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visit SimpleNode");
   }

   public Object visit (ASTprogram node, Object data) {
      st = (SymbolTable) data;
      variablesRead = st.getVariables();
      variablesWritten = st.getVariables();
      functions = st.getFunctions();

      node.childrenAccept(this, data); // visit all children of program node

      if (!variablesWritten.isEmpty()) {
         allVariablesWrittenTo = false;
      }
      if (!variablesRead.isEmpty()) {
         allVariablesReadFrom = false;
      }
      if (!functions.isEmpty()) {
         allFuncsCalled = false;
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
      if(!existsDuplicateVar) {
         System.out.println("Pass: All Variables Declared Only Once!");
      }
      else {
         System.out.println("Fail: Duplicate Variables Defined!");
      }
      if (allFuncsCalled) {
         System.out.println("Pass: All Functions Called!");
      }
      else {
         System.out.println("Fail: Functions " + functions + " Called!");
      }
      if (correctAssignmentType) {
         System.out.println("Pass: All Variables Assigned Correct Type!");
      }
      if (arithmeticOnIntegers) {
         System.out.println("Pass: Arithmetic Expressions on Integers Only!");
      }
      else {
         System.out.println("Fail: Arithmetic Expressions on Non-Integers!");
      }
      if (logicOnBooleans) {
         System.out.println("Pass: Logic Expressions on Booleans Only!");
      }
      else {
         System.out.println("Fail: Logic Expressions on Non-Booleans!");
      }

      return DataType.Program;
   }

   public Object visit (ASTmain node, Object data) {
      scope = "main";
      node.jjtGetChild(0).jjtAccept(this, data);
      scope = "global";
      return DataType.Main;
   }

   public Object visit (ASTfunction_declarations node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTfunction node, Object data) {
      SimpleNode child = (SimpleNode) node.jjtGetChild(1);
      String id = (String) child.value;
      scope = id;
      node.childrenAccept(this, data);
      scope = "global";
      return DataType.Func;
   }

   public Object visit (ASTstatement_block node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTstatement node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTexpression node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTcondition node, Object data) {
      SimpleNode child1 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
      String type1 = child1.toString();

      if(type1.equals("rhs_identifier")) {
         String id = (String) child1.value;
         type1 = st.getType(scope, id);
      }

      SimpleNode child2 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
      String type2 = child1.toString();

      if(type2.equals("rhs_identifier")) {
         String id = (String) child1.value;
         type2 = st.getType(scope, id);
      }

      if(!type1.equals("integer") && !type2.equals("integer")) {
         arithmeticOnIntegers = false;
      }

      return node.childrenAccept(this, data);
   }

   public Object visit (ASTfragment node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTfunction_call node, Object data) {
      SimpleNode child = (SimpleNode) node.jjtGetChild(0);
      String id = (String) child.value;
      functions.remove(id);
      return node.childrenAccept(this, data);
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
      SimpleNode child1 = (SimpleNode) node.jjtGetChild(0);
      String id = (String) child1.value;
      String type1 = st.getType(scope, id);

      SimpleNode child2 = (SimpleNode) node.jjtGetChild(1).jjtGetChild(0);
      String type2 = child2.toString();

      if(type2.equals("binary_arith_op")) {
         type2 = "integer";
      }
      else if(type2.equals("binary_logic_op")) {
         type2 = "boolean";
      }
      else {
         SimpleNode func = (SimpleNode) child2.jjtGetChild(0);
         String funcId = (String) func.value;
         type2 = st.getType("global", funcId);
      }

      if(!type1.equals(type2)) {
         correctAssignmentType = false;
         System.out.println("Fail: Variable Type " + type1 + " Assigned " + type2 + " Value!");
      }

      return node.childrenAccept(this, data);
   }

   public Object visit (ASTdeclaration node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTprint node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTif_statement node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTwhile_loop node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTlhs_identifier node, Object data) {
      SimpleNode parent = (SimpleNode) node.jjtGetParent();
      String type = parent.toString();
      String value = (String) node.value;
      if(type.equals("assignment")) {
         if(!st.lookup(value, scope)) {
            declaredBeforeUse = false;
            System.out.println("Fail: " + value + " Not Declared Before Use!");
         }
      }
      variablesWritten.remove(value + scope);
      return DataType.Id;
   }

   public Object visit (ASTrhs_identifier node, Object data) {
      String value = (String) node.value;
      variablesRead.remove(value + scope);
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

   public Object visit (ASTparameter_list node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTparameter node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTargument_list node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTargument node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTbinary_arith_op node, Object data) {
      SimpleNode child1 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
      String type1 = child1.toString();

      if(type1.equals("rhs_identifier")) {
         String id = (String) child1.value;
         type1 = st.getType(scope, id);
      }

      SimpleNode child2 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
      String type2 = child1.toString();

      if(type2.equals("rhs_identifier")) {
         String id = (String) child1.value;
         type2 = st.getType(scope, id);
      }

      if(!type1.equals("integer") || !type2.equals("integer")) {
         arithmeticOnIntegers = false;
      }

      return node.childrenAccept(this, data);
   }

   public Object visit (ASTarith_op node, Object data) {
      return DataType.ArithOp;
   }

   public Object visit (ASTbinary_logic_op node, Object data) {
      SimpleNode child1 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
      String type1 = child1.toString();

      if(type1.equals("rhs_identifier")) {
         String id = (String) child1.value;
         type1 = st.getType(scope, id);
      }

      SimpleNode child2 = (SimpleNode) node.jjtGetChild(0).jjtGetChild(0);
      String type2 = child1.toString();

      if(type2.equals("rhs_identifier")) {
         String id = (String) child1.value;
         type2 = st.getType(scope, id);
      }

      if(!type1.equals("boolean") || !type2.equals("boolean")) {
         logicOnBooleans = false;
      }

      return node.childrenAccept(this, data);
   }

   public Object visit (ASTlogic_op node, Object data) {
      return DataType.LogicOp;
   }

   public Object visit (ASTcomp_op node, Object data) {
      return DataType.CompOp;
   }

}

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
   private Boolean arrayElementsSameType = true;
   private Boolean indexWithinBounds = true;

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
      if (arrayElementsSameType) {
         System.out.println("Pass: Array Elements are Same Type as Declared!");
      }
      if (indexWithinBounds) {
         System.out.println("Pass: All Indexs Within Bounds!");
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

   public Object visit (ASTparameter_list node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTparameter node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTstatement_block node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTstatement node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTassignment node, Object data) {
      SimpleNode child1 = (SimpleNode) node.jjtGetChild(0);
      String id = (String) child1.value;
      String type1 = st.getType(scope, id);

      SimpleNode child2 = (SimpleNode) node.jjtGetChild(1).jjtGetChild(0);
      String type2 = child2.toString();

      if(type2.equals("fragment")) {
         SimpleNode c2 = (SimpleNode) child2.jjtGetChild(0);
         type2 = c2.toString();
         if(type2.equals("rhs_identifier")) {
            String id2 = (String) c2.value;
            if(id2 == null) {
               c2 = (SimpleNode) c2.jjtGetChild(0);
               id2 = (String) c2.value;
               System.out.println(id2);
            }
            type2 = st.getType(scope, id2);
         }
      }
      else {
         SimpleNode func = (SimpleNode) child2.jjtGetChild(0);
         String funcId = (String) func.value;
         type2 = st.getType("global", funcId);
      }

      if(type2.equals("array")) {
         SimpleNode c2 = (SimpleNode) child2.jjtGetChild(0).jjtGetChild(0).jjtGetChild(0);
         type2 = c2.toString();
         if(type2.equals("rhs_identifier")) {
            String id2 = (String) c2.value;
            if(id2 == null) {
               c2 = (SimpleNode) c2.jjtGetChild(0);
               id2 = (String) c2.value;
            }
            type2 = st.getType(scope, id2);
         }
         else if(type2.equals("integer")) {
            type2 = "int[]";
         }
         else {
            type2 = "string[]";
         }
      }

      if(!type1.equals(type2)) {
         correctAssignmentType = false;
         System.out.println("Fail: Variable Type " + type1 + " Assigned " + type2 + " Value!");
         System.out.println(id + " Assigned " + child2.value + " " + node.jjtGetChild(1).jjtGetChild(0));
      }

      return node.childrenAccept(this, data);
   }

   public Object visit (ASTdeclaration node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTarray_declaration node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit(ASTarray_size node, Object data) {
      return DataType.Integer;
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

   public Object visit (ASTfunction_call node, Object data) {
      SimpleNode child = (SimpleNode) node.jjtGetChild(0);
      String id = (String) child.value;
      functions.remove(id);
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTargument_list node, Object data) {
      return node.childrenAccept(this, data);
   }

   public Object visit (ASTargument node, Object data) {
      return node.jjtGetChild(0).jjtAccept(this, data);
   }

   public Object visit (ASTexpression node, Object data) {
      if(node.jjtGetNumChildren() == 1) {
         return node.jjtGetChild(0).jjtAccept(this, data);
      }
      else {
         SimpleNode arg1 = (SimpleNode) node.jjtGetChild(0);
         String type1 = arg1.toString();
         if(type1.equals("fragment")) {
            arg1 = (SimpleNode) arg1.jjtGetChild(0);
            type1 = arg1.toString();
         }
         String id1 = "";

         if(type1.equals("rhs_identifier")) {
            id1 = (String) arg1.value;
            type1 = st.getType(scope, id1);
         }
         else if(type1.equals("integer") || type1.equals("boolean")) {
            id1 = (String) arg1.value;
         }

         SimpleNode arg2 = (SimpleNode) node.jjtGetChild(2);
         String type2 = arg2.toString();
         if(type2.equals("fragment")) {
            arg2 = (SimpleNode) arg2.jjtGetChild(0);
            type2 = arg2.toString();
         }
         String id2 = "";

         if(type2.equals("rhs_identifier")) {
            id2 = (String) arg2.value;
            type2 = st.getType(scope, id2);
         }
         else if(type2.equals("integer") || type2.equals("boolean")) {
            id2 = (String) arg2.value;
         }

         if(type1.equals("integer") && !type2.equals("integer")) {
            arithmeticOnIntegers = false;
         }
         else if(type1.equals("boolean") && !type2.equals("boolean")) {
            logicOnBooleans = false;
         }

         return node.childrenAccept(this, data);
      }
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

   public Object visit (ASTinteger node, Object data) {
      return DataType.Integer;
   }

   public Object visit (ASTstring node, Object data) {
      return DataType.String;
   }

   public Object visit (ASTbool node, Object data) {
      return DataType.Boolean;
   }

   public Object visit(ASTarray node, Object data) {
      SimpleNode parent = (SimpleNode) node.jjtGetParent();
      String arrayType = parent.toString();
      SimpleNode child;
      String type;

      if(arrayType.equals("fragment")) {
         parent = (SimpleNode) parent.jjtGetParent().jjtGetParent();
         arrayType = parent.toString();
         if(arrayType.equals("assignment")) {
            parent = (SimpleNode) parent.jjtGetChild(0);
            arrayType = st.getType(scope, ((String) parent.value));
            arrayType = arrayType.substring(0, arrayType.length() - 2);
            if(arrayType.equals("int")) {
               arrayType = "integer";
            }
         }
      }

      for(int i = 0; i < node.jjtGetNumChildren(); i++) {
         child = (SimpleNode) node.jjtGetChild(i).jjtGetChild(0);
         type = child.toString();
         if(!arrayType.equals(type)) {
            arrayElementsSameType = false;
            System.out.println("Fail: Array of Type " + arrayType + "[] Contains Element of Type " + type + "!");
         }
      }

      return node.childrenAccept(this, data);
   }

   public Object visit (ASTarray_index node, Object data) {
      SimpleNode child1 = (SimpleNode) node.jjtGetChild(0);
      String id = (String) child1.value;
      String sizeStr = st.getArraySize(scope, id);
      int size = Integer.parseInt(sizeStr);

      SimpleNode child2 = (SimpleNode) node.jjtGetChild(1);
      String indexStr = (String) child2.value;
      int index = Integer.parseInt(indexStr);

      if(index >= size && index < 0) {
         System.out.println("Fail: Index " + index + " Not Within Bounds!");
         indexWithinBounds = false;
      }

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

   public Object visit (ASTarith_op node, Object data) {
      return DataType.ArithOp;
   }

   public Object visit (ASTlogic_op node, Object data) {
      return DataType.LogicOp;
   }

   public Object visit (ASTcomp_op node, Object data) {
      return DataType.CompOp;
   }

}

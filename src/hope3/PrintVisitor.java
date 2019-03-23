public class PrintVisitor implements HOPE3Visitor {

   private String indentation = "    ";

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visit SimpleNode");
   }

   public Object visit (ASTprogram node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // statement_block
      return data;
   }

   public Object visit (ASTstatement_block node, Object data) {
      if (node.jjtGetNumChildren() != 0) {
         node.jjtGetChild(0).jjtAccept(this, data); //statement
         System.out.println(" ;");
         node.jjtGetChild(1).jjtAccept(this, data); //statement_block
      }
      return data;
   }

   public Object visit (ASTstatement node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // assignment or declaration or print
      return data;
   }

   public Object visit (ASTexpression node, Object data) {
      if (node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
         System.out.print(" ");
         node.jjtGetChild(1).jjtAccept(this, data); // op
         System.out.print(" ");
         node.jjtGetChild(2).jjtAccept(this, data); // fragment
      }
      return data;
   }

   public Object visit (ASTfragment node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // identifier or int or str or bool
      return data;
   }

   public Object visit (ASTinteger node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTstring node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTbool node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTassignment node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // identifier
      System.out.print(" = ");
      node.jjtGetChild(1).jjtAccept(this, data); // expression
      return data;
   }

   public Object visit (ASTdeclaration node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // type
      System.out.print(" ");
      node.jjtGetChild(1).jjtAccept(this, data); // identifier
      return data;
   }

   public Object visit (ASTprint node, Object data) {
      System.out.print("print ( ");
      node.jjtGetChild(0).jjtAccept(this, data); // expression
      System.out.print(" )");
      return data;
   }

   public Object visit (ASTlhs_identifier node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTrhs_identifier node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTtype node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTbinary_arith_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit (ASTbinary_logic_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

}

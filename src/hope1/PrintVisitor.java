public class PrintVisitor implements HOPE1Visitor {

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
         node.jjtGetChild(1).jjtAccept(this, data); //statement_block
      }
      return data;
   }

   public Object visit (ASTstatement node, Object data) {
      System.out.print("print (");
      node.jjtGetChild(0).jjtAccept(this, data); // expression
      System.out.println(")");
      return data;
   }

   public Object visit (ASTexpression node, Object data) {
      if (node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
         System.out.print(" ");
         node.jjtGetChild(1).jjtAccept(this, data); // binary_arith_op
         System.out.print(" ");
         node.jjtGetChild(2).jjtAccept(this, data); // fragment
      }
      return data;
   }

   public Object visit (ASTfragment node, Object data) {
      System.out.print (node.value);
      return data;
   }

   public Object visit (ASTbinary_arith_op node, Object data) {
      System.out.print (node.value);
      return data;
   }

}

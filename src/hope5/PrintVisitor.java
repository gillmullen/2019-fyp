public class PrintVisitor implements HOPE5Visitor {

   private String indentation = "    ";

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visit SimpleNode");
   }

   public Object visit(ASTprogram node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // statement_block
      return data;
   }

   public Object visit(ASTstatement_block node, Object data) {
      if (node.jjtGetNumChildren() != 0) {
         node.jjtGetChild(0).jjtAccept(this, data); //statement
         node.jjtGetChild(1).jjtAccept(this, data); //statement_block
      }
      return data;
   }

   public Object visit(ASTstatement node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // assignment or declaration or print or if
      return data;
   }

   public Object visit(ASTexpression node, Object data) {
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

   public Object visit(ASTcondition node, Object data) {
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

   public Object visit(ASTfragment node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      return data;
   }

   public Object visit(ASTinteger node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTstring node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTbool node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTassignment node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // identifier
      node.jjtGetChild(1).jjtAccept(this, data); // expression
      System.out.print(" ;");
      return data;
   }

   public Object visit(ASTdeclaration node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // type
      System.out.print(" ");
      node.jjtGetChild(1).jjtAccept(this, data); // identifier
      System.out.print(" = ");
      node.jjtGetChild(2).jjtAccept(this, data); // expression
      System.out.println(" ;");
      return data;
   }

   public Object visit(ASTprint node, Object data) {
      System.out.print("print ( ");
      node.jjtGetChild(0).jjtAccept(this, data); // expression
      System.out.print(" )");
      System.out.println(" ;");
      return data;
   }

   public Object visit(ASTif_statement node, Object data) {
      System.out.print("if ( ");
      node.jjtGetChild(0).jjtAccept(this, data); // condition
      System.out.println(" ) {");
      node.jjtGetChild(1).jjtAccept(this, data); // statement_block
      System.out.println("\n}\nelse { ");
      node.jjtGetChild(2).jjtAccept(this, data); // statement_block
      System.out.println("}");
      return data;
   }

   public Object visit(ASTwhile_loop node, Object data) {
      System.out.print("while ( ");
      node.jjtGetChild(0).jjtAccept(this, data); // condition
      System.out.println(" ) {");
      node.jjtGetChild(1).jjtAccept(this, data); // statement_block
      System.out.println("}");
      return data;
   }

   public Object visit(ASTidentifier node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTtype node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTbinary_arith_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTbinary_logic_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTcomp_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

}

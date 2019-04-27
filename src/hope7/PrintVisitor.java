public class PrintVisitor implements HOPE7Visitor {

   private String indentation = "    ";

   public Object visit(SimpleNode node, Object data) {
      throw new RuntimeException("Visit SimpleNode");
   }

   public Object visit(ASTprogram node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // function_declarations
      node.jjtGetChild(1).jjtAccept(this, data); // main
      return data;
   }

   public Object visit(ASTmain node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // statement_block
      return data;
   }

   public Object visit(ASTfunction_declarations node, Object data) {
      if (node.jjtGetNumChildren() != 0) {
         node.jjtGetChild(0).jjtAccept(this, data); // function
         node.jjtGetChild(1).jjtAccept(this, data); // function_declarations
      }
      return data;
   }

   public Object visit(ASTfunction node, Object data) {
      System.out.print("def ");
      node.jjtGetChild(0).jjtAccept(this, data); // type
      System.out.print(" ");
      node.jjtGetChild(1).jjtAccept(this, data); // identifier
      System.out.print("( ");
      node.jjtGetChild(2).jjtAccept(this, data); // parameter_list
      System.out.println(" ) {");
      node.jjtGetChild(3).jjtAccept(this, data); // statement_block
      System.out.print("return ( ");
      if (node.jjtGetNumChildren() == 5) {
         node.jjtGetChild(4).jjtAccept(this, data); // fragment
      }
      System.out.println(" ) ;\n}\n");
      return data;
   }

   public Object visit(ASTparameter_list node, Object data) {
      if (node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // parameter
      }
      else {
         node.jjtGetChild(0).jjtAccept(this, data); // parameter
         System.out.print(" , ");
         node.jjtGetChild(1).jjtAccept(this, data); // parameter
      }
      return data;
   }

   public Object visit(ASTparameter node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // type
      System.out.print(" ");
      node.jjtGetChild(1).jjtAccept(this, data); // identifier
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

   public Object visit(ASTassignment node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // identifier
      System.out.print(" = ");
      node.jjtGetChild(1).jjtAccept(this, data); // expression
      System.out.println(" ;");
      return data;
   }

   public Object visit(ASTdeclaration node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // type
      System.out.print(" ");
      node.jjtGetChild(1).jjtAccept(this, data); // identifier
      System.out.println(" ;");
      return data;
   }

   public Object visit(ASTarray_declaration node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // type
      System.out.print("[");
      node.jjtGetChild(1).jjtAccept(this, data); // size
      System.out.print("] ");
      node.jjtGetChild(2).jjtAccept(this, data); // identifier
      System.out.println(" ;");
      return data;
   }

   public Object visit(ASTarray_size node, Object data) {
      System.out.print(node.value);
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

   public Object visit(ASTfunction_call node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // identifier
      System.out.print(" ( ");
      node.jjtGetChild(1).jjtAccept(this, data); // argument_list
      System.out.print(" )");
      return data;
   }

   public Object visit(ASTargument_list node, Object data) {
      if (node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // argument
      }
      else {
         node.jjtGetChild(0).jjtAccept(this, data); // argument
         System.out.print(" , ");
         node.jjtGetChild(1).jjtAccept(this, data); // argument
      }
      return data;
   }

   public Object visit(ASTargument node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data); // fragment
      return data;
   }

   public Object visit(ASTexpression node, Object data) {
      if (node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         for(int i = 0; i < node.jjtGetNumChildren(); i++) {
            if(i != 0) {
               System.out.print(" ");
            }
            node.jjtGetChild(i).jjtAccept(this, data);
         }
      }
      return data;
   }

   public Object visit(ASTcondition node, Object data) {
      if (node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         for(int i = 0; i < node.jjtGetNumChildren(); i++) {
            if(i != 0) {
               System.out.print(" ");
            }
            node.jjtGetChild(i).jjtAccept(this, data);
         }
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

   public Object visit(ASTfloating_point node, Object data) {
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

   public Object visit(ASTarray node, Object data) {
      System.out.print("[ ");
      if(node.jjtGetNumChildren() == 1) {
         node.jjtGetChild(0).jjtAccept(this, data); // fragment
      }
      else {
         for(int i = 0; i < node.jjtGetNumChildren(); i++) {
            if(i != 0) {
               System.out.print(", ");
            }
            node.jjtGetChild(i).jjtAccept(this, data);
         }
      }
      System.out.print(" ]");
      return data;
   }

   public Object visit(ASTarray_index node, Object data) {
      node.jjtGetChild(0).jjtAccept(this, data);
      System.out.print(" [ ");
      node.jjtGetChild(1).jjtAccept(this, data);
      System.out.print(" ]");
      return data;
   }

   public Object visit(ASTlhs_identifier node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTrhs_identifier node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTtype node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTarith_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTlogic_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

   public Object visit(ASTcomp_op node, Object data) {
      System.out.print(node.value);
      return data;
   }

}

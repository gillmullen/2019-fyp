/* Generated By:JavaCC: Do not edit this line. HOPE7Visitor.java Version 5.0 */
public interface HOPE7Visitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTprogram node, Object data);
  public Object visit(ASTmain node, Object data);
  public Object visit(ASTfunction_declarations node, Object data);
  public Object visit(ASTfunction node, Object data);
  public Object visit(ASTparameter_list node, Object data);
  public Object visit(ASTparameter node, Object data);
  public Object visit(ASTstatement_block node, Object data);
  public Object visit(ASTstatement node, Object data);
  public Object visit(ASTassignment node, Object data);
  public Object visit(ASTdeclaration node, Object data);
  public Object visit(ASTarray_declaration node, Object data);
  public Object visit(ASTarray_size node, Object data);
  public Object visit(ASTprint node, Object data);
  public Object visit(ASTif_statement node, Object data);
  public Object visit(ASTwhile_loop node, Object data);
  public Object visit(ASTfunction_call node, Object data);
  public Object visit(ASTargument_list node, Object data);
  public Object visit(ASTargument node, Object data);
  public Object visit(ASTexpression node, Object data);
  public Object visit(ASTcondition node, Object data);
  public Object visit(ASTfragment node, Object data);
  public Object visit(ASTinteger node, Object data);
  public Object visit(ASTstring node, Object data);
  public Object visit(ASTbool node, Object data);
  public Object visit(ASTarray node, Object data);
  public Object visit(ASTlhs_identifier node, Object data);
  public Object visit(ASTrhs_identifier node, Object data);
  public Object visit(ASTtype node, Object data);
  public Object visit(ASTbinary_arith_op node, Object data);
  public Object visit(ASTarith_op node, Object data);
  public Object visit(ASTbinary_logic_op node, Object data);
  public Object visit(ASTlogic_op node, Object data);
  public Object visit(ASTcomp_op node, Object data);
}
/* JavaCC - OriginalChecksum=0029c08ec3cc4871eb7d364acb4a0cb8 (do not edit this line) */

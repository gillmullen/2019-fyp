/* Generated By:JavaCC: Do not edit this line. HOPE6Visitor.java Version 5.0 */
public interface HOPE6Visitor
{
  public Object visit(SimpleNode node, Object data);
  public Object visit(ASTprogram node, Object data);
  public Object visit(ASTstatement_block node, Object data);
  public Object visit(ASTstatement node, Object data);
  public Object visit(ASTexpression node, Object data);
  public Object visit(ASTcondition node, Object data);
  public Object visit(ASTinteger node, Object data);
  public Object visit(ASTstring node, Object data);
  public Object visit(ASTbool node, Object data);
  public Object visit(ASTassignment node, Object data);
  public Object visit(ASTdeclaration node, Object data);
  public Object visit(ASTprint node, Object data);
  public Object visit(ASTif_statement node, Object data);
  public Object visit(ASTwhile_loop node, Object data);
  public Object visit(ASTarray node, Object data);
  public Object visit(ASTidentifier node, Object data);
  public Object visit(ASTtype node, Object data);
  public Object visit(ASTbinary_arith_op node, Object data);
  public Object visit(ASTbinary_logic_op node, Object data);
  public Object visit(ASTcomp_op node, Object data);
}
/* JavaCC - OriginalChecksum=6fbee0e72c4b1866e9be5f92538d6898 (do not edit this line) */

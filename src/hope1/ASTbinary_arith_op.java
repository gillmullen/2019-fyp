/* Generated By:JJTree: Do not edit this line. ASTbinary_arith_op.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTbinary_arith_op extends SimpleNode {
  public ASTbinary_arith_op(int id) {
    super(id);
  }

  public ASTbinary_arith_op(HOPE1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=1e795c375db8321d15c13e63f2884433 (do not edit this line) */

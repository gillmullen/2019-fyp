/* Generated By:JJTree: Do not edit this line. ASTstatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTstatement extends SimpleNode {
  public ASTstatement(int id) {
    super(id);
  }

  public ASTstatement(HOPE3 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE3Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=fda8a2aad26048f4f54b968ac4a33758 (do not edit this line) */

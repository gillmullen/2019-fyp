/* Generated By:JJTree: Do not edit this line. ASTinteger.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTinteger extends SimpleNode {
  public ASTinteger(int id) {
    super(id);
  }

  public ASTinteger(HOPE1 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE1Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5c62f73966d3876f79c3ed0695d3a91c (do not edit this line) */

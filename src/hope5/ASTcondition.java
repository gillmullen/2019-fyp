/* Generated By:JJTree: Do not edit this line. ASTcondition.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTcondition extends SimpleNode {
  public ASTcondition(int id) {
    super(id);
  }

  public ASTcondition(HOPE5 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE5Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=4c5de16e9327577d028144873aa0f8a4 (do not edit this line) */

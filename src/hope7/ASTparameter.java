/* Generated By:JJTree: Do not edit this line. ASTparameter.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTparameter extends SimpleNode {
  public ASTparameter(int id) {
    super(id);
  }

  public ASTparameter(HOPE7 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE7Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=59b1d67332362fa09273af593a4b988b (do not edit this line) */

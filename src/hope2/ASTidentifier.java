/* Generated By:JJTree: Do not edit this line. ASTidentifier.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTidentifier extends SimpleNode {
  public ASTidentifier(int id) {
    super(id);
  }

  public ASTidentifier(HOPE2 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE2Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=848c319cddaa618eeeb4b6c837267ac3 (do not edit this line) */

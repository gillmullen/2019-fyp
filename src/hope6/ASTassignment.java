/* Generated By:JJTree: Do not edit this line. ASTassignment.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTassignment extends SimpleNode {
  public ASTassignment(int id) {
    super(id);
  }

  public ASTassignment(HOPE6 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE6Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=62361e6ea77d34fe1f05d865fee026c9 (do not edit this line) */

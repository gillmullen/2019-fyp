/* Generated By:JJTree: Do not edit this line. ASTdeclaration.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTdeclaration extends SimpleNode {
  public ASTdeclaration(int id) {
    super(id);
  }

  public ASTdeclaration(HOPEFUL p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPEFULVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=5a48ac688ecae12b2b3b33761a93e013 (do not edit this line) */
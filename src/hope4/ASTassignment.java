/* Generated By:JJTree: Do not edit this line. ASTassignment.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
public
class ASTassignment extends SimpleNode {
  public ASTassignment(int id) {
    super(id);
  }

  public ASTassignment(HOPE4 p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(HOPE4Visitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=866f7c7f6c12b29976a304a8eae380de (do not edit this line) */
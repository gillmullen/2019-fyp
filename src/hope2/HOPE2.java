/* Generated By:JJTree&JavaCC: Do not edit this line. HOPE2.java */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class HOPE2/*@bgen(jjtree)*/implements HOPE2TreeConstants, HOPE2Constants {/*@bgen(jjtree)*/
  protected static JJTHOPE2State jjtree = new JJTHOPE2State();
   public static SymbolTable st = new SymbolTable();

   public static void main(String args[]) {

      HOPE2 parser = null;
      String irFileName = "HOPE2.ll";

      if(args.length == 0) {
         System.out.println("Reading input...");
         parser = new HOPE2(System.in);
      }
      else if(args.length == 1) {
         try {
            parser = new HOPE2(new FileInputStream(args[0]));
            irFileName = args[0] + ".ll";
         }
         catch(FileNotFoundException e) {
            System.err.println("File " + args[0] + " not found");
            return;
         }
      }
      else {
         System.out.println("You must use the following format:");
         System.out.println("java HOPE2 inputfile");
      }

      try{
         SimpleNode root = parser.program();

         System.out.println("Abstract Syntax Tree:");
         root.dump(">");

         System.out.println("\u005cnProgram:");
         PrintVisitor pv = new PrintVisitor();
         root.jjtAccept(pv, "");

         System.out.println("\u005cnSymbol Table:");
         st.print();

         System.out.println("\u005cnSemantic Analysis:");
         SemanticVisitor sv = new SemanticVisitor();
         root.jjtAccept(sv, st);

         System.out.println("\u005cnWriting LLVM code to: " + irFileName);

         try {
            Context c = new Context(new BufferedWriter(new FileWriter(irFileName)));
            IrCodeVisitor irCode = new IrCodeVisitor();
            root.jjtAccept(irCode, c);
            c.buffer.flush();
         }
         catch (IOException e) {
            System.out.println("Failed to write LLVM code to file");
            e.printStackTrace(System.out);
         }

      }
      catch(ParseException e) {
         System.out.println(e.getMessage());
      }
   }

/* Section 4: Grammar */
  static final public SimpleNode program() throws ParseException {
                        /*@bgen(jjtree) program */
  ASTprogram jjtn000 = new ASTprogram(JJTPROGRAM);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      statement_block();
      jj_consume_token(0);
     jjtree.closeNodeScope(jjtn000, true);
     jjtc000 = false;
     {if (true) return jjtn000;}
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
    throw new Error("Missing return statement in function");
  }

  static final public void statement_block() throws ParseException {
                          /*@bgen(jjtree) statement_block */
  ASTstatement_block jjtn000 = new ASTstatement_block(JJTSTATEMENT_BLOCK);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PRINT:
      case INT:
      case ID:
        statement();
        statement_block();
        break;
      default:
        jj_la1[0] = jj_gen;
                                     jjtree.closeNodeScope(jjtn000, true);
                                     jjtc000 = false;

      }
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public void statement() throws ParseException {
                    /*@bgen(jjtree) statement */
  ASTstatement jjtn000 = new ASTstatement(JJTSTATEMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PRINT:
        print();
        break;
      case ID:
        assignment();
        break;
      case INT:
        declaration();
        break;
      default:
        jj_la1[1] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public void expression() throws ParseException {
    fragment();
    label_1:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS_SIGN:
      case MINUS_SIGN:
      case MUL_SIGN:
      case DIV_SIGN:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_1;
      }
      binary_arith_op();
                                   ASTexpression jjtn001 = new ASTexpression(JJTEXPRESSION);
                                   boolean jjtc001 = true;
                                   jjtree.openNodeScope(jjtn001);
      try {
        fragment();
      } catch (Throwable jjte001) {
                                   if (jjtc001) {
                                     jjtree.clearNodeScope(jjtn001);
                                     jjtc001 = false;
                                   } else {
                                     jjtree.popNode();
                                   }
                                   if (jjte001 instanceof RuntimeException) {
                                     {if (true) throw (RuntimeException)jjte001;}
                                   }
                                   if (jjte001 instanceof ParseException) {
                                     {if (true) throw (ParseException)jjte001;}
                                   }
                                   {if (true) throw (Error)jjte001;}
      } finally {
                                   if (jjtc001) {
                                     jjtree.closeNodeScope(jjtn001,  3);
                                   }
      }
    }
  }

  static final public void fragment() throws ParseException {
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case NUM:
      integer();
      break;
    case ID:
      rhs_identifier();
      break;
    default:
      jj_la1[3] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  static final public void integer() throws ParseException {
                  /*@bgen(jjtree) integer */
                   ASTinteger jjtn000 = new ASTinteger(JJTINTEGER);
                   boolean jjtc000 = true;
                   jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(NUM);
                    jjtree.closeNodeScope(jjtn000, true);
                    jjtc000 = false;
                    jjtn000.value = t.image;
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public void assignment() throws ParseException {
                     /*@bgen(jjtree) assignment */
  ASTassignment jjtn000 = new ASTassignment(JJTASSIGNMENT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      lhs_identifier();
      jj_consume_token(EQUALS);
      expression();
      jj_consume_token(SEMIC);
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public void declaration() throws ParseException {
                      /*@bgen(jjtree) declaration */
                       ASTdeclaration jjtn000 = new ASTdeclaration(JJTDECLARATION);
                       boolean jjtc000 = true;
                       jjtree.openNodeScope(jjtn000);String id;
    try {
      type();
      id = lhs_identifier();
      jj_consume_token(SEMIC);
                                          jjtree.closeNodeScope(jjtn000, true);
                                          jjtc000 = false;
                                          st.insert(id);
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public void print() throws ParseException {
                /*@bgen(jjtree) print */
  ASTprint jjtn000 = new ASTprint(JJTPRINT);
  boolean jjtc000 = true;
  jjtree.openNodeScope(jjtn000);
    try {
      jj_consume_token(PRINT);
      jj_consume_token(LBR);
      expression();
      jj_consume_token(RBR);
      jj_consume_token(SEMIC);
    } catch (Throwable jjte000) {
     if (jjtc000) {
       jjtree.clearNodeScope(jjtn000);
       jjtc000 = false;
     } else {
       jjtree.popNode();
     }
     if (jjte000 instanceof RuntimeException) {
       {if (true) throw (RuntimeException)jjte000;}
     }
     if (jjte000 instanceof ParseException) {
       {if (true) throw (ParseException)jjte000;}
     }
     {if (true) throw (Error)jjte000;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static final public String lhs_identifier() throws ParseException {
                           /*@bgen(jjtree) lhs_identifier */
                            ASTlhs_identifier jjtn000 = new ASTlhs_identifier(JJTLHS_IDENTIFIER);
                            boolean jjtc000 = true;
                            jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(ID);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                  jjtn000.value = t.image; {if (true) return t.image;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String rhs_identifier() throws ParseException {
                           /*@bgen(jjtree) rhs_identifier */
                            ASTrhs_identifier jjtn000 = new ASTrhs_identifier(JJTRHS_IDENTIFIER);
                            boolean jjtc000 = true;
                            jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(ID);
                  jjtree.closeNodeScope(jjtn000, true);
                  jjtc000 = false;
                  jjtn000.value = t.image; {if (true) return t.image;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
    throw new Error("Missing return statement in function");
  }

  static final public String type() throws ParseException {
                 /*@bgen(jjtree) type */
                  ASTtype jjtn000 = new ASTtype(JJTTYPE);
                  boolean jjtc000 = true;
                  jjtree.openNodeScope(jjtn000);Token t;
    try {
      t = jj_consume_token(INT);
                   jjtree.closeNodeScope(jjtn000, true);
                   jjtc000 = false;
                   jjtn000.value = t.image; {if (true) return t.image;}
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
    throw new Error("Missing return statement in function");
  }

  static final public void binary_arith_op() throws ParseException {
                          /*@bgen(jjtree) binary_arith_op */
                           ASTbinary_arith_op jjtn000 = new ASTbinary_arith_op(JJTBINARY_ARITH_OP);
                           boolean jjtc000 = true;
                           jjtree.openNodeScope(jjtn000);Token t;
    try {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case PLUS_SIGN:
        t = jj_consume_token(PLUS_SIGN);
        break;
      case MINUS_SIGN:
        t = jj_consume_token(MINUS_SIGN);
        break;
      case MUL_SIGN:
        t = jj_consume_token(MUL_SIGN);
        break;
      case DIV_SIGN:
        t = jj_consume_token(DIV_SIGN);
        break;
      default:
        jj_la1[4] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                                                                              jjtree.closeNodeScope(jjtn000, true);
                                                                              jjtc000 = false;
                                                                              jjtn000.value = t.image;
    } finally {
     if (jjtc000) {
       jjtree.closeNodeScope(jjtn000, true);
     }
    }
  }

  static private boolean jj_initialized_once = false;
  /** Generated Token Manager. */
  static public HOPE2TokenManager token_source;
  static JavaCharStream jj_input_stream;
  /** Current token. */
  static public Token token;
  /** Next token. */
  static public Token jj_nt;
  static private int jj_ntk;
  static private int jj_gen;
  static final private int[] jj_la1 = new int[5];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x2006000,0x2006000,0x3c0000,0x2800000,0x3c0000,};
   }

  /** Constructor with InputStream. */
  public HOPE2(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public HOPE2(java.io.InputStream stream, String encoding) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser.  ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new HOPE2TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  static public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public HOPE2(java.io.Reader stream) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new HOPE2TokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  static public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public HOPE2(HOPE2TokenManager tm) {
    if (jj_initialized_once) {
      System.out.println("ERROR: Second call to constructor of static parser. ");
      System.out.println("       You must either use ReInit() or set the JavaCC option STATIC to false");
      System.out.println("       during parser generation.");
      throw new Error();
    }
    jj_initialized_once = true;
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(HOPE2TokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jjtree.reset();
    jj_gen = 0;
    for (int i = 0; i < 5; i++) jj_la1[i] = -1;
  }

  static private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  static final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  static final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  static private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  static private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  static private int[] jj_expentry;
  static private int jj_kind = -1;

  /** Generate ParseException. */
  static public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[28];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 5; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 28; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  static final public void enable_tracing() {
  }

  /** Disable tracing. */
  static final public void disable_tracing() {
  }

}

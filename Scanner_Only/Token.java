class Token {

    public final static int ELSE = 1;
    public final static int IF = 2;
    public final static int INT = 3;
    public final static int RETURN = 4;
    public final static int VOID = 5;
    public final static int WHILE = 6;

    public final static int PLUS = 7;
    public final static int MINUS = 8;
    public final static int TIMES = 9;
    public final static int OVER = 10;

    public final static int LT = 11;
    public final static int LTEQ = 12;
    public final static int GT = 13;
    public final static int GTEQ = 14;
    public final static int EQ = 15;
    public final static int NOTEQ = 16;

    public final static int ASSIGN = 17;
    public final static int SEMI = 18;
    public final static int COMMA = 19;
    public final static int LPAREN = 20;
    public final static int RPAREN = 21;

    public final static int LSQUARE = 22;
    public final static int RSQUARE = 23;

    public final static int LCURL = 24;
    public final static int RCURL = 25;

    public final static int ID = 26;
    public final static int NUM = 27;

    public final static int ERROR = 28;



  public int m_type;
  public String m_value;
  public int m_line;
  public int m_column;
  
  Token (int type, String value, int line, int column) {
    m_type = type;
    m_value = value;
    m_line = line;
    m_column = column;
  }

  public String toString() {
    switch (m_type) {
      case ELSE:
        return "ELSE";
      case IF:
        return "IF";
      case INT:
        return "INT";
      case RETURN:
        return "RETURN";
      case VOID:
        return "VOID";
      case WHILE:
        return "WHILE";
      
      case PLUS:
        return "PLUS";
      case MINUS:
        return "MINUS";
      case TIMES:
        return "TIMES";
      case OVER:
        return "OVER";
     
      case LTEQ:
        return "LTEQ";
      case LT:
        return "LT";
      case GT:
        return "GT";
      case GTEQ:
        return "GTEQ";
      case EQ:
        return "EQ";
      case NOTEQ:
        return "NOTEQ";

      case ASSIGN:
        return "ASSIGN";
      case SEMI:
        return "SEMI";
      case COMMA:
        return "COMMA";
      case LPAREN:
        return "LPAREN";
      case RPAREN:
        return "RPAREN";

      case LSQUARE:
        return "LSQUARE";
      case RSQUARE:
          return "RSQUARE";

      case LCURL:
          return "LCURL";
      case RCURL:
          return "RCURL";

      case ID:
        return "ID(" + m_value + ")";
      case NUM:
        return "NUM(" + m_value + ")";

      case ERROR:
        return "ERROR"

      default:
        return "UNKNOWN(" + m_value + ")";
    }
  }
}


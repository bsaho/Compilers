package absyn;

public class WhileExp extends Exp {
  public Exp test;
  public Exp body;
  public WhileExp( int pos, Exp test, Exp body ) {
    this.pos = pos+1;
    this.test = test;
    this.body = body;
  }
}

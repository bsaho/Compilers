package absyn;

public class IfExp extends Exp {
  public Exp test;
  public Exp thenp;
  public Exp elsep;
  public IfExp( int pos, Exp test, Exp thenp, Exp elsep ) {
    this.pos = pos;
    this.test = test;
    this.thenp = thenp;
    this.elsep = elsep;
  }
}

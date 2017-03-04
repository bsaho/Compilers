package absyn;

public class IfExp extends Exp {
  public Exp test;
  public Exp then;
  public Exp elsep;
  public IfExp( int pos, Exp test, Exp thenpart, Exp elsep ) {
    this.pos = pos;
    this.test = test;
    this.then = then;
    this.elsep = elsep;
  }
}

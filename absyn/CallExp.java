package absyn;

public class CallExp extends Exp {
  public String func;
  public ExpList args;
  public CallExp( int pos, String func, ExpList args) {
    this.func = func;
    this.args = args;
  }
}

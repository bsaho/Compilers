package absyn;

public class VarExp extends Exp {
  public Var variable;
  public VarExp( int pos, Var variable ) {
    this.pos = pos+1;
    this.variable = variable;
  }
}

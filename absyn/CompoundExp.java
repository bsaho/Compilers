package absyn;

public class CompoundExp extends Exp {
  public VarDecList decs;
  public ExpList exps;
  public CompoundExp( int pos, VarDecList decs, ExpList exps ) {
    this.pos = pos+1;
    this.decs = decs;
    this.exps = exps;
  }
}

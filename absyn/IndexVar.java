package absyn;

public class IndexVar extends Var {
  public String name;
  public Exp index;
  public IndexVar( int pos, String name, Exp index ) {
    this.pos = pos+1;
    this.name = name;
    this.index = index;
  }
}

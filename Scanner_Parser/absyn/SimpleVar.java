package absyn;

public class SimpleVar extends Var {
  public String name;
  public RepeatExp( int pos, String name ) {
    this.pos = pos;
    this.name = name;
  }
}
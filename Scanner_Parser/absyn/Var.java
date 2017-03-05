package absyn;

abstract public class Var extends Absyn {
 public String name;
  public Var( int pos, String name ) {
    this.pos = pos;
    this.name = name;
  }



}
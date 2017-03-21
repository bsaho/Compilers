package absyn;

public class IntExp extends Exp {
  public int value;
  public IntExp( int pos, int value ) {
    this.pos = pos+1;
    this.value = value;
  }
}

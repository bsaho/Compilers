package absyn;

public class NameTy{

  public int pos;
  public int typ;

  public final static int INT = 0;
  public final static int VOID = 1;

  public NameTy( int pos, int typ ) {
    this.pos = pos;
    this.typ = typ;
  }
}
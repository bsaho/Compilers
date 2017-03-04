package absyn;

public class NameTy{

  public int pos;
  public int typ;

  final static int NameTy.INT, NameTy.VOID;

  public VarDec( int pos, int typ ) {
    this.pos = pos;
    this.typ = typ;
  }
}
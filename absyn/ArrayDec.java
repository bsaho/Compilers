package absyn;

public class ArrayDec extends VarDec  {
  	public NameTy typ;
  	public String name;
    public IntExp size;
    public boolean hasSize;

  public ArrayDec(int pos, NameTy typ, String name, IntExp size)  {
    this.pos=pos;
    this.typ = typ;
    this.name = name;
    this.size=size;
    hasSize = true;
  }

  public ArrayDec(int pos, NameTy typ, String name)  {
    this.pos=pos;
    this.typ = typ;
    this.name = name;
    hasSize = false;
  }

}

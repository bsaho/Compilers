package absyn;

public class SimpleDec extends VarDec  {
  public NameTy typ;
  public String name;

  public SimpleDec(int pos, NameTy typ, String name)
  {
  	this.pos=pos+1;
    this.typ = typ;
    this.name = name;
  }
}

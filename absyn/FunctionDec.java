package absyn;

public class FunctionDec extends Dec  {
    public NameTy result;
    public String func;
    public VarDecList params;
    public CompoundExp body;

  public FunctionDec(int pos, NameTy result, String func,VarDecList params,
  CompoundExp body)
  {
  	this.pos=pos+1;
    this.result = result;
    this.func = func;
    this.params=params;
    this.body=body;
  }
}

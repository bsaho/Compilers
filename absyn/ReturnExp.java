package absyn;

public class ReturnExp extends Exp {
  public Exp exp;
  public ReturnExp(int pos, Exp exp){
    this.pos = pos+1;
    this.exp = exp;
  }
}

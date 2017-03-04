package absyn;

public class DecList{
  public Dec head;
  public Dec tail;

  public DecList(Dec head, DecList tail)  {
    this.head = head;
    this.tail = tail;
  }
}

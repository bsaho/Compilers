package absyn;

abstract public class Absyn {
  public int pos;

  final static int SPACES = 4;

  static private void indent( int spaces ) {
    for( int i = 0; i < spaces; i++ ) System.out.print( " " );
  }

  static public void showTree( ExpList tree, int spaces ) {
    while( tree != null ) {
      showTree( tree.head, spaces );
      tree = tree.tail;
    } 
  }

  static private void showTree( Exp tree, int spaces ) {
    // if( tree instanceof AssignExp )
    //   showTree( (AssignExp)tree, spaces );
     if( tree instanceof IfExp )
      showTree( (IfExp)tree, spaces );
    else if( tree instanceof IntExp )
      showTree( (IntExp)tree, spaces );
    else if( tree instanceof OpExp )
      showTree( (OpExp)tree, spaces );
    else if( tree instanceof VarExp )
      showTree( (VarExp)tree, spaces );
    else if (tree instanceof WhileExp)
      showTree ((WhileExp) tree, spaces);
    else if (tree instanceof ReturnExp)
      showTree ((ReturnExp) tree, spaces);
    else if (tree instanceof CallExp )
      showTree ((CallExp) tree, spaces);
    // else if (tree instanceof Var)
    //   showTree ((Var) tree, spaces);
    else {
      indent( spaces );
      System.out.println( "Illegal expression at line " + tree.pos  );
    }
  }
  static private void showTree( Var tree, int spaces ) {
        indent( spaces );
    System.out.println( "Var:" + tree.name );


  }

  // static private void showTree( AssignExp tree, int spaces ) {
  //   indent( spaces );
  //   System.out.println( "AssignExp:" );
  //   spaces += SPACES;
  //   showTree( tree.lhs, spaces );
  //   showTree( tree.rhs, spaces );
  // }

  static private void showTree( IfExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "IfExp:" );
    spaces += SPACES;
    showTree( tree.test, spaces );
    showTree( tree.thenp, spaces );
    showTree( tree.elsep, spaces );
  }

  static private void showTree( IndexVar tree, int spaces ) {
    indent( spaces );
    System.out.println( "IndexVar:" + tree.name );
    spaces += SPACES;
    showTree( tree.index, spaces );
  }

  static private void showTree( WhileExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "WhileExp:"  );
    spaces += SPACES;
    showTree( tree.test, spaces );
    showTree( tree.body, spaces );

  }


  static private void showTree( ReturnExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "ReturnExp: " );
    showTree (tree.exp, spaces); 
  }



  static private void showTree( IntExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "IntExp: " + tree.value ); 
  }


  static private void showTree( SimpleVar tree, int spaces ) {
    indent( spaces );
    System.out.println( "SimpleVar: " + tree.name ); 
  }

  static private void showTree( CallExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "CallExp: " + tree.func ); 
    showTree (tree.args, spaces);
  }



  static private void showTree( OpExp tree, int spaces ) {
    indent( spaces );
    System.out.print( "OpExp:" ); 
    switch( tree.op ) {
      case OpExp.PLUS:
        System.out.println( " + " );
        break;
      case OpExp.MINUS:
        System.out.println( " - " );
        break;
      case OpExp.MUL:
        System.out.println( " * " );
        break;
      case OpExp.DIV:
        System.out.println( " / " );
        break;
      case OpExp.EQ:
        System.out.println( " = " );
        break;
      case OpExp.LT:
        System.out.println( " < " );
        break;
      case OpExp.GT:
        System.out.println( " > " );
        break;
      default:
        System.out.println( "Unrecognized operator at line " + tree.pos);
    }
    spaces += SPACES;
    showTree( tree.left, spaces );
    showTree( tree.right, spaces ); 
  }



  static private void showTree( VarExp tree, int spaces ) {
    indent( spaces );
    System.out.println( "VarExp: " + tree.variable );
  }

}

package absyn;
import java.util.*;

abstract public class Absyn 
{
	public int pos;

  	final static int SPACES = 4;

  	static private void indent( int spaces ) 
  	{
	    for( int i = 0; i < spaces; i++ )
	    {
	    	System.out.print( " " );
	    }		
  	}
  
	static public void showTree( DecList tree, int spaces ) 
	{
	    while( tree != null ) 
	    {
	    	showTree( tree.head, spaces );
	      	tree = tree.tail;
	    } 
	  	//System.out.print("\n");
	}
  	
  	//Exp list for parameters
	static public void showTree( ExpList tree, int spaces, int isParam) 
	{
	    while( tree != null ) 
	    {
	    	showTree(tree.head, spaces );
	     	tree = tree.tail;
	    } 
	}

	//Exp list for normal lists
	static public void showTree( ExpList tree, int spaces) 
	{
		ArrayList<Exp> backlist = new ArrayList<Exp>();

		//Move through the list of Exp and add to backlist
	    while( tree != null ) 
	    {
	      	backlist.add(tree.head);
	     	tree = tree.tail;
	    } 

	    //Display Exp in reverse order
	    for (int i = backlist.size()-1; i >= 0 ; i-- ) 
	    {
	    	showTree(backlist.get(i), spaces);
	    }
	}

	//VarDecList for parameters
	static public void showTree( VarDecList tree, int spaces, int isParam) 
  	{
	  	while( tree != null ) 
	    {
	    	showTree(tree.head, spaces );
	     	tree = tree.tail;
	    } 
  	}

  	//VarDecList for normal lists
  	static public void showTree( VarDecList tree, int spaces ) 
  	{
	   ArrayList<VarDec> backlist = new ArrayList<VarDec>();

		//Move through the list of VarDec and add to backlist
	    while( tree != null ) 
	    {
	      	backlist.add(tree.head);
	     	tree = tree.tail;
	    } 

	    //Display VarDec in reverse order
	    for (int i = backlist.size()-1; i >= 0 ; i-- ) 
	    {
	    	showTree(backlist.get(i), spaces);
	    }
  	}


	static public void showTree(Exp tree, int spaces ) 
	{
	    if( tree instanceof AssignExp )
	       showTree( (AssignExp)tree, spaces );
	    else if( tree instanceof IfExp )
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
	    else if (tree instanceof NilExp )
	      showTree ((NilExp) tree, spaces);
	    else if (tree instanceof CompoundExp )
	      showTree ((CompoundExp) tree, spaces);
	    else 
	    {
	      indent( spaces );
	      System.out.println( "Illegal expression at line " + tree.pos  );
	    }
	}

	static public void showTree(Var tree, int spaces ) 
	{
	    if( tree instanceof SimpleVar )
	      showTree( (SimpleVar )tree, spaces );
	    else if( tree instanceof IndexVar )
	      showTree( (IndexVar )tree, spaces );
	    else 
	    {
	      indent( spaces );
	      System.out.println( "Illegal expression at line " + tree.pos  );
	    }
	}

  	static public void showTree(Dec tree, int spaces ) 
  	{
	    if (tree instanceof FunctionDec)
	      showTree ((FunctionDec) tree, spaces);
	    else if (tree instanceof SimpleDec)
	      showTree ((SimpleDec) tree, spaces);
	    else if (tree instanceof ArrayDec)
	      showTree ((ArrayDec) tree, spaces); 
	    else 
	    {
	      indent( spaces );
	      System.out.println( "Illegal expression at line " + tree.pos  );
	    }
	}

	static public void showTree(NameTy tree, int spaces ) 
	{
	    //indent( spaces );
	    if (tree.typ == NameTy.INT) 
	    {
	        System.out.print( "Int " );
	    }
	    else if (tree.typ == NameTy.VOID) 
	    {
	        System.out.print( "Void " );
	    }
	}

    static public void showTree( CompoundExp  tree, int spaces ) 
    {
	    indent( spaces );
	    showTree( tree.decs, spaces );
	    showTree( tree.exps, spaces );
  	}

  	static public void showTree( FunctionDec tree, int spaces ) 
  	{
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print("FunctionDec: Name: " + tree.func );
	    spaces += SPACES;
	    System.out.print(" Type: ");
	    showTree (tree.result, spaces );
	    showTree (tree.params, spaces, 1);
	    //System.out.println(" ");
	    showTree (tree.body, spaces );
  	}

    static public void showTree( IntExp tree, int spaces ) 
    {
    	System.out.println(" ");
	   	indent( spaces );
	    System.out.print( "IntExp: " + tree.value + " "); 
  	}

  	static public void showTree( SimpleDec tree, int spaces ) 
  	{
  		System.out.println(" ");
    	indent( spaces );
	    System.out.print( "Simple Dec: Type: ");
	    showTree(tree.typ, spaces);
	    System.out.print( "Name: " + tree.name );
  	}

 	static public void showTree( ArrayDec tree, int spaces ) 
 	{
 		System.out.println(" ");
	    indent( spaces );
	    System.out.print( "Array Dec: Type: ");
	    showTree(tree.typ, spaces);
	    System.out.print( "Name: " + tree.name );
	    spaces += SPACES;
	    if (tree.hasSize == true) 
	    {
			showTree (tree.size, spaces );
	    }
  	}


  	static public void showTree( AssignExp tree, int spaces ) 
  	{	
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print( "AssignExp: " );
	    spaces += SPACES;
	    showTree( tree.lhs, spaces);
	    //System.out.println(" ");
	    showTree( tree.rhs, spaces );
  	}

  	static public void showTree( IfExp tree, int spaces ) 
  	{
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print( "IfExp:" );
	    spaces += SPACES;
	    showTree( tree.test, spaces );
	    showTree( tree.thenp, spaces );
	    if (tree.elsep != null )
	    {
	    	showTree( tree.elsep, spaces );
	  	}
  	}

  	static public void showTree( IndexVar tree, int spaces ) 
  	{
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print( "IndexVar:" + tree.name + " " );
	    spaces += SPACES;
	    showTree( tree.index, spaces );
  	}

  	static public void showTree( WhileExp tree, int spaces ) 
  	{
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print("WhileExp:"  );
	    spaces += SPACES;
	    showTree( tree.test, spaces );
	    showTree( tree.body, spaces );
  	}

  	static public void showTree( ReturnExp tree, int spaces ) 
  	{
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print( "ReturnExp: " );
	    spaces += SPACES;
	    showTree (tree.exp, spaces); 
  	}

  	static public void showTree( SimpleVar tree, int spaces ) 
  	{
  		System.out.println(" ");
	    indent( spaces );
	    System.out.print( "SimpleVar: " + tree.name + " "); 
  	}

  	static public void showTree( CallExp tree, int spaces ) 
  	{
  		System.out.println(" "); 
	    indent( spaces );
	    System.out.print( "CallExp: " + tree.func + " " ); 
	    //System.out.println(" ");
	    spaces += SPACES;
	    showTree (tree.args, spaces, 1);
	    //System.out.print("\n");
  	}

  	static public void showTree( OpExp tree, int spaces ) 
  	{
  		System.out.println(" "); 
	    indent( spaces );
	    System.out.print( "OpExp:" ); 
	    switch( tree.op ) 
	    {
	      case OpExp.PLUS:
	        System.out.print( " + " );
	        break;
	      case OpExp.MINUS:
	        System.out.print( " - " );
	        break;
	      case OpExp.MUL:
	        System.out.print( " * " );
	        break;
	      case OpExp.DIV:
	        System.out.print( " / " );
	        break;
	      case OpExp.EQ:
	        System.out.print( " = " );
	        break;
	      case OpExp.NE:
	        System.out.print( " != " );
	        break;
	      case OpExp.LT:
	        System.out.print( " < " );
	        break;
	      case OpExp.LE:
	        System.out.print( " <= " );
	        break;
	      case OpExp.GT:
	        System.out.print( " > " );
	        break;
	      case OpExp.GE:
	        System.out.print( " >= " );
	        break;
	      default:
	        System.out.println( "Unrecognized operator at line " + tree.pos);
	    }
	    spaces += SPACES;
	    //System.out.println(" ");
	    showTree( tree.left, spaces);
	   // System.out.println(" ");
	    showTree( tree.right, spaces); 
	   // System.out.print("\n");
  	}

  	static public void showTree( NilExp tree, int spaces ) 
  	{
  		System.out.println(" ");
    	indent( spaces );
    	System.out.print( "Nil" );
  	}

  	static public void showTree( VarExp tree, int spaces ) 
  	{
    	//indent( spaces );
    	//System.out.print( " VarExp: ");
    	showTree(tree.variable,spaces);
  	}  
}

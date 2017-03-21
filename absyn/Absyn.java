package absyn;
import java.util.*;
import cminus.Main;

abstract public class Absyn 
{
	public static int genericScopeCounter=0;
	public int pos;
    public static  SymbolTable t=new SymbolTable ();
    public static char flagOption;


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
		//Retrive flag option from Main arguments
		flagOption = Main.getFlag();
		
        if(flagOption == 'a')
        {
            System.out.println( "\nThe Abstract Syntax Tree is:\n" );
        }
        else if(flagOption == 's')
        {
            System.out.println( "\nThe Symbol Table is:\n" );
        }

	    while( tree != null ) 
	    {
	    	showTree( tree.head, spaces );
	      	tree = tree.tail;
	    } 
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

	static public String showTree(Var tree, int spaces ) 
	{
	    if( tree instanceof SimpleVar )
        {
	       String varName=showTree( (SimpleVar )tree, spaces );
	       return varName;
	    }
	    else if( tree instanceof IndexVar )
        {
	       showTree( (IndexVar )tree, spaces );
	  	   return "";
	  	}
	    else 
	    {
	      indent( spaces );
	      System.out.println( "Illegal Variable Declarion at line " + tree.pos  );
	      return "";
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

    static public String showTree(  NameTy tree, int spaces ) 
    {
        indent( spaces );
        if (tree.typ == NameTy.INT) 
        {
           System.out.print( "Int " );
           return "Int";
        }
        else if (tree.typ == NameTy.VOID) 
        {
           System.out.print( "Void " );
           return "VOID";
        }
        return "";
    }

    static public void showTree( CompoundExp  tree, int spaces ) 
    {
	    //indent( spaces );
	    showTree( tree.decs, spaces );
	    showTree( tree.exps, spaces );
  	}

  	static public void showTree( FunctionDec tree, int spaces ) 
  	{

      	System.out.println("");
        indent( spaces );
        System.out.print("FunctionDec: Name: " + tree.func );
        spaces += SPACES;
        System.out.print(" ReturnType: ");

	    t.add (tree.func,"FUNC",tree.pos);
	    t.addScope (tree.func,"FUNC", tree.pos);
	    
        String result=showTree(tree.result, 0 );
       // System.out.println("");
	    
        showTree (tree.params, spaces, 1);
	    showTree (tree.body, spaces );
	    //t.printAll ();
	    if (result.equals("VOID") && t.lookup ("ReturnExp",0))
        {
            System.out.println(" ");
	    	System.out.println (" Error,illegal return statement, " + " void function " 
	    		+ tree.func + " cannot return value");    	
	    }
        else if (result.equals ("Int") && t.lookup ("ReturnNull",0))
        {
            System.out.println(" ");
	    	System.out.println (" Error,illegal return statement, " + " int function " 
	    		+ tree.func + " must return value other than null");
	    }
        else if (result.equals ("Int") && !t.lookup ("ReturnExp",0))
        {
            System.out.println(" ");
	    	System.out.println (" Error,no return statement, " + " int function " 
	    		+ tree.func + " must return int value");
	    }
        t.delete();
  	}

    static public int showTree( IntExp tree, int spaces ) 
    {	
        if (flagOption=='a')
        {
        	System.out.println(" ");
    	   	indent( spaces );
    	    System.out.print( "IntExp: " + tree.value + " "); 
		}
	    return tree.value;
  	}

    static public void showTree( SimpleDec tree, int spaces )
    {
        System.out.println(" ");
        indent( spaces );
        System.out.print( "Simple Dec: Type: ");

        String varName=showTree(tree.typ, 0);

        System.out.print( "Name: " + tree.name );
        
        if (t.lookup (tree.name,0)==true)
        {
            System.out.println(" ");
         	System.out.println ("Error, redeclaration of existing variable " + tree.name + " at line " + tree.pos);
        }
        t.add (tree.name,varName,1);  
    }

 	static public void showTree( ArrayDec tree, int spaces ) 
 	{
 		
     	System.out.println(" ");
    	indent( spaces );
    	System.out.print( "Array Dec: Type: ");
		

	    String varName=showTree(tree.typ, spaces);
	    
        if (varName.equals ("VOID"))
        {
            System.out.println(" ");
	    	System.out.println ("Error, array variable " + tree.name + " on line " +tree.pos + " must be of type INT  "  );
	    	return;
	    }
	    
        System.out.print( "Name: " + tree.name + " ");
	   
        spaces += SPACES;

	    
	    if (t.lookup (tree.name,0)==true) 
        {
            System.out.println(" ");
     		System.out.println ("Error, redeclaration of existing variable " + tree.name + " at line " + tree.pos);
     	}

	    if (tree.hasSize == true) 
	    {	 
            int size= showTree (tree.size, spaces );  
	    	t.add (tree.name,varName,size,1);
	    }
  	}


  	static public void showTree( AssignExp tree, int spaces ) 
  	{	
  		if (flagOption=='a')
        {
      		System.out.println(" ");
    	    indent( spaces );
    	    System.out.print( "AssignExp: " );
		}
	    spaces += SPACES;
	    String varName=showTree( tree.lhs, spaces);
	   	
        if (varName.length ()>0)
        {
   		    if (!t.lookup (varName,0))
            {
                System.out.println(" ");
   		    	System.out.println ("Error, assigning to undeclared variable " + varName + " on line  " + tree.pos);
   		    }
   		    else if (tree.rhs instanceof VarExp)
            {
                String searchString= showTree ((VarExp) tree.rhs,spaces);
   		    	if (searchString.length ()>0 && !t.lookup (searchString,0))
                {
                    System.out.println(" ");
		    	   	System.out.println ("Error, assigning from undeclared variable " + searchString 
		    	   		  + "on line  " + tree.pos);
		    	}
                else if (searchString.length ()>0)
                {
		    		String lhsName= t.lookup (varName,"Search");
		    		String rhsName=t.lookup (searchString,"Search");
		    		if (!rhsName.equals(lhsName))
                    {
                        System.out.println(" ");
		    		    System.out.println ("Error, mismatched types on line  " + tree.pos);
                    }
		        }
   	        }
   	        else if (tree.rhs instanceof OpExp)
            {
   	        	int opNum=showTree((OpExp) tree.rhs,spaces);
   	        	if (opNum>4)
                {
   	        		System.out.print ("Error on line " + tree.pos + " cannot use assignment with Symbol ");
   	        		if (opNum==5) System.out.println ("<");
   	        		else if (opNum==6) System.out.println ("<=");
   	        		else if (opNum==7) System.out.println (">.");
   	        		else if (opNum==8) System.out.println (">=.");
   	        		else if (opNum==9) System.out.println ("=.");
   	        	}
   	        }
	   	}
	    
	   // showTree( tree.rhs, spaces );
  	}

  	static public void showTree( IfExp tree, int spaces ) {
  		
        System.out.println(" ");
        indent( spaces );
        if (flagOption=='a')
        {
    	    System.out.print( "IfExp:" );
		}
	    spaces += SPACES;

	    String scopeName= "IfScope" + String.valueOf(genericScopeCounter);
	    genericScopeCounter++;
        
        if (flagOption == 's') 
        {
            System.out.print(scopeName); 
        }
	   
	  	    if (tree.test instanceof VarExp)
            {
    	    	String varName= showTree((VarExp) tree.test,spaces);
    	    	if (varName.length()>0)
                {
    	    		if (!t.lookup(varName,"Search").equals("Int"))
                    {
                       System.out.println(" ");
    	               System.out.println ("Error on line " + tree.pos + ", condition must be INT");
    	    		}
                    else if (!t.lookup (varName))
                    {
                        System.out.println(" ");
    	    			System.out.println ("Error on line " + tree.pos + ", condition must exist");
    	    		}
    	       }
	    }
  		t.addScope (scopeName,"IF",tree.pos);

	    showTree( tree.test, spaces );
	    showTree( tree.thenp, spaces );
	    if (tree.elsep != null )
	    {
	    	showTree( tree.elsep, spaces );
	  	}
	  	t.delete ();
  	}

  	static public void showTree( IndexVar tree, int spaces ) 
  	{
  		if (flagOption=='a')
        {
      		System.out.println(" ");
    	    indent( spaces );
    	    System.out.print( "IndexVar:" + tree.name + " " );
		}

	    spaces += SPACES;
	    if (tree.index instanceof VarExp){
	    	//fix later
	    	String searchString=  showTree((VarExp) tree.index,spaces);
	    	if (searchString.length ()>0)
            {
		    	if (!t.lookup(searchString,0))
                {
                    System.out.println(" ");
		    		System.out.println (" Error, array index variable at "+ tree.pos + " ,  does not exist");
		    	}
		    	String searchResult=t.lookup (searchString,"Search");
		    	if (t.lookup(searchString,0) && !searchResult.equals("Int"))
                {
                    System.out.println(" ");
                    System.out.println (" Error at  "+ tree.pos + " array index must be int");
		    	}
            }
	    }
	    showTree( tree.index, spaces );

  	}

  	static public void showTree( WhileExp tree, int spaces ) {
  		
        System.out.println(" ");
        indent( spaces );

        if(flagOption == 'a')
  		{
			System.out.print("WhileExp:"  );
  		}

	    spaces += SPACES;
	    
        String scopeName= "WhileScope" + String.valueOf(genericScopeCounter);
        genericScopeCounter++;
        t.addScope (scopeName,"WHILE",tree.pos);
	    
        if (flagOption == 's')
    	{	
            System.out.print(scopeName); 	
    	}

	    showTree( tree.test, spaces );
	    showTree( tree.body, spaces );

	   t.delete ();
  	}

  	static public void showTree( ReturnExp tree, int spaces ) 
  	{
  		if(flagOption == 'a')
  		{
  			System.out.println(" ");
	    	indent( spaces );
	    	System.out.print( "ReturnExp: " );
  		}

	    
	    if (flagOption == 's')
    	{
    		if (tree.exp instanceof NilExp)
    		{
	   			t.add ("ReturnNull","RETURN",tree.pos);
			}
			else 
			{
				t.add ("ReturnExp","RETURN",tree.pos);
			}
    	}

	    spaces += SPACES;
	    showTree (tree.exp, spaces); 
  	}

  	static public String showTree( SimpleVar tree, int spaces ) {
  		if(flagOption == 'a')
  		{
  			System.out.println(" ");
	    	indent( spaces ); 
	    	System.out.print( "SimpleVar: " + tree.name + " ");
  		}
	    return tree.name; 
  	}

static public void showTree( CallExp tree, int spaces ) {
  		if(flagOption == 'a')
  		{
  			System.out.println(" "); 
	    	indent( spaces );
	    	System.out.print( "CallExp: " + tree.func + " " ); 
  		}
	    
	    if (flagOption == 's')
	   	{
	   		if (!t.lookup (tree.func))
	   		{
                System.out.println(" ");
	    		System.out.println (" Error, failed function call on line "+ tree.pos + " , function does not exist");
	    	}
	    }
	   
	    //System.out.println(" ");
	    spaces += SPACES;
	    showTree (tree.args, spaces, 1);
	    //System.out.print("\n");
  	}
 static public int showTree( OpExp tree, int spaces ) {
  		if(flagOption == 'a')
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
  		}
  		
	    spaces += SPACES;
	    //System.out.println(" ");
	    showTree( tree.left, spaces);
	   // System.out.println(" ");
	    showTree( tree.right, spaces); 
	   // System.out.print("\n");
	    return tree.op;
  	}

  	static public void showTree( NilExp tree, int spaces ) 
    {
  		if(flagOption == 'a')
  		{
  			System.out.println(" ");
    		indent( spaces );
    		System.out.print( "Nil" );
  		}	
  	}

  	static public String showTree( VarExp tree, int spaces )
    {
    	return showTree(tree.variable,spaces);
  	}  
}

package codeGen;

import java.util.*;
import absyn.*;
import cminus.Main;

public class CodeGen
{
    public static char flagOption;
    public static int currentOutLine = 0;
    static int emitLoc = 0;
static int highEmitLoc = 0;
static int entry; /* absolute address for main */
static int globalOffset; /* next available loc after global frame */

private static final String FILENAME = "output.tm";

public static int emitSkip( int distance ) {
     int i = emitLoc;
     emitLoc += distance;
     if( highEmitLoc < emitLoc )
     {highEmitLoc = emitLoc;}
     return i;
}
public static void emitBackup( int loc ) {
 if( loc > highEmitLoc )
{ //emitComment( "BUG in emitBackup" );
System.out.println ("BUG in emitBackup");
 } emitLoc = loc;
}
public static void emitRestore(  ) {
 emitLoc = highEmitLoc;
}

// public static void emitRM_Abs( String op,int r, int a, String c ) {
//     String content= Integer.toString(emitLoc) +": " + op +" " +Integer.toString(r) +", " + Integer.toString((a-(emitLoc+1))) + "(" + c +" )";
    
//      buffFileWriter.write (content);
//      ++emitLoc;
//       if( TraceCode ){ 
//         content="\t" + c;
//         buffFileWriter.write ( content);
//         }
//      if( highEmitLoc < emitLoc ){
//           highEmitLoc = emitLoc;}
// }

public static void start( DecList tree )
    {
        //Retrive flag option from Main arguments
        flagOption = Main.getFlag();

        if (flagOption == 'c') 
        {
            System.out.println( "\nCode Generation Start:\n" );
            genPreludeCode ();

            codeGen(tree);
        }   
    } 

    static public void genPreludeCode()
    {
        System.out.println( "* Standard prelude:" );

        //Standard prelude code
        System.out.println( "0:    LD  6,0(0)    load gp with maxaddress" );
        System.out.println( "1:    LDA  5,0(6)   copy to gp to fp" );
        System.out.println( "2:    ST  0,0(0)    clear location 0" );

        //Code for input function
        System.out.println( "4:    ST  0,-1(5)   store return" );
        System.out.println( "5:    IN  0,0,0     input" );
        System.out.println( "6:    LD  7,-1(5)   return to caller" );

        //Code for output function
        System.out.println( "7:    ST  0,-1(5)   store return" );
        System.out.println( "8:    LD  0,-2(5)   load output value" );
        System.out.println( "9:    OUT  0,0,0    output" );
        System.out.println( "10:   LD  7,-1(5)   return to caller" );
        System.out.println( "3:    LDA  7,7(7)   jump around i/o code" );

        System.out.println( "* End of standard prelude." );





    }


    static public void codeGen(DecList tree) 
    {
        while( tree != null ) 
        {
            codeGen(tree.head);
            tree = tree.tail;
        } 
    }
    
    //Exp list for parameters
    static public void codeGen(ExpList tree, int isParam) 
    {
        while( tree != null ) 
        {
            codeGen(tree.head);
            tree = tree.tail;
        } 
    }

    //Exp list for normal lists
    static public void codeGen(ExpList tree) 
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
            codeGen(backlist.get(i));
        }
    }

    //VarDecList for parameters
    static public void codeGen(VarDecList tree, int isParam) 
    {
        while( tree != null ) 
        {
            codeGen(tree.head);

            tree = tree.tail;
        } 
    }

    //VarDecList for normal lists
    static public void codeGen(VarDecList tree) 
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
            codeGen(backlist.get(i));
        }
    }


    static public void codeGen(Exp tree) 
    {
        if( tree instanceof AssignExp )
           codeGen( (AssignExp)tree );
        else if( tree instanceof IfExp )
          codeGen( (IfExp)tree );
        else if( tree instanceof IntExp )
          codeGen( (IntExp)tree );
        else if( tree instanceof OpExp )
          codeGen( (OpExp)tree );
        else if( tree instanceof VarExp )
          codeGen( (VarExp)tree );
        else if (tree instanceof WhileExp)
          codeGen ((WhileExp) tree );
        else if (tree instanceof ReturnExp)
          codeGen ((ReturnExp) tree );
        else if (tree instanceof CallExp )
          codeGen ((CallExp) tree );
        else if (tree instanceof NilExp )
          codeGen ((NilExp) tree );
        else if (tree instanceof CompoundExp )
          codeGen ((CompoundExp) tree);
    }

    static public void codeGen(Var tree) 
    {
        if( tree instanceof SimpleVar )
        {
            codeGen( (SimpleVar)tree );
        }
        else if( tree instanceof IndexVar )
        {
            codeGen( (IndexVar)tree );
        }
    }

    static public void codeGen(Dec tree) 
    {
        if (tree instanceof FunctionDec)
          codeGen( (FunctionDec)tree );
        else if (tree instanceof SimpleDec)
          codeGen( (SimpleDec)tree );
        else if (tree instanceof ArrayDec)
          codeGen( (ArrayDec)tree ); 
        else 
        {
          System.out.println( "Illegal expression at line " + tree.pos  );
        }
    }

    static public void codeGen(NameTy tree) 
    {
        if (tree.typ == NameTy.INT) 
        {
           System.out.print( "Int " );
        }
        else if (tree.typ == NameTy.VOID) 
        {
           System.out.print( "Void " );
        }
    }

    static public void codeGen(CompoundExp tree) 
    {
        codeGen(tree.decs);
        codeGen(tree.exps);
    }

    static public void codeGen(FunctionDec tree) 
    {
        System.out.print("FunctionDec: Name: " + tree.func );

  
        codeGen(tree.result);  
        codeGen(tree.params);
        codeGen(tree.body);  
    }

    static public void codeGen(IntExp tree) 
    {   
        System.out.print( "IntExp: " + tree.value + " ");  
    }

    static public void codeGen(SimpleDec tree)
    {
        System.out.print( "Simple Dec: Type: ");
        codeGen(tree.typ);
        System.out.print( "Name: " + tree.name );
    }

    static public void codeGen(ArrayDec tree) 
    {   
        System.out.print( "Array Dec: Type: ");       
        codeGen(tree.typ);   
    }


    static public void codeGen(AssignExp tree) 
    {   

        System.out.print( "AssignExp: " );     
        codeGen(tree.lhs);
        codeGen(tree.rhs);
    }

    static public void codeGen(IfExp tree) 
    {

        System.out.print( "IfExp:" );
        codeGen(tree.test);
        codeGen(tree.thenp);
        
        if (tree.elsep != null )
        {
            codeGen( tree.elsep);
        }
    }

    static public void codeGen(IndexVar tree) 
    {

        System.out.print( "IndexVar:" + tree.name + " " );
        codeGen(tree.index);
    }

    static public void codeGen(WhileExp tree) 
    {
        System.out.print("WhileExp:");

        codeGen(tree.test);
        codeGen(tree.body);
    }

    static public void codeGen(ReturnExp tree) 
    {

        System.out.print( "ReturnExp: " );
        codeGen(tree.exp); 
    }

    static public void codeGen(SimpleVar tree) 
    {
       
        System.out.print( "SimpleVar: " + tree.name + " ");
    }

    static public void codeGen(CallExp tree) 
    {
       
        System.out.print( "CallExp: " + tree.func + " " ); 
        codeGen (tree.args);
    }

    static public void codeGen(OpExp tree) 
    {
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

        codeGen(tree.left);
        codeGen(tree.right); 
    }

    static public void codeGen(NilExp tree) 
    {
        System.out.print( "Nil" );
    }

    static public void codeGen(VarExp tree)
    {
        codeGen(tree.variable);
    }  
    
}

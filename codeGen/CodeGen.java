package codeGen;

import java.util.*;
import absyn.*;
import cminus.Main;


public class CodeGen
{
    public static char flagOption;
    static int emitLoc = 0;
    static int highEmitLoc = 0;
    static int entry; /* absolute address for main */
    static int globalOffset; /* next available loc after global frame */

    static String currentScope;

    static int pc = 7; //The program counter register
    static int gp = 6; //The global pointer register
    static int fp = 5; //The frame pointer register
    static int ac = 0; //The 1st storage register
    static int ac1 = 1; //The 2nd storage register


    static int frameOffset = 0;

    static int ofpFO = 0; // Original frame pointer - Frame Offset
    static int retFO = -1; // Return Adress - Frame Offset
    static int initFO = -2; // Start of function parameters - Frame Offset

    private static final String FILENAME = "output.tm";

    public static SymbolTable table = Absyn.t;

    public static int emitSkip( int distance )
    {
        int i = emitLoc;
        emitLoc += distance;
        if( highEmitLoc < emitLoc )
        {
            highEmitLoc = emitLoc;
        }
        return i;
    }

    public static void emitBackup( int loc ) 
    {
        if( loc > highEmitLoc )
        { 
            //emitComment( "BUG in emitBackup" );
            System.out.println ("BUG in emitBackup");
        } 
        emitLoc = loc;
    }
    
    public static void emitRestore(  ) 
    {
        emitLoc = highEmitLoc;
    }


    //Format: (Operation, register, register, register, comment)
    public static void emitRO(String op,int r, int s, int t, String c )
    {
        String content = Integer.toString(emitLoc) +": " + op +" " +Integer.toString(r) +"," + Integer.toString(s) + "," + Integer.toString(t);
        
        System.out.println (content);

       // buffFileWriter.write (content);
        ++emitLoc;
        // if( TraceCode )
        // { 
        //     content="\t" + c;
        //     buffFileWriter.write ( content);
        // }
        
        if( highEmitLoc < emitLoc )
        {
            highEmitLoc = emitLoc;
        }
    }

    //Format: (Operation, register, offset, register, comment)
    public static void emitRM(String op,int r, int d, int s, String c )
    {
        String content = Integer.toString(emitLoc) +": " + op +" " +Integer.toString(r) +"," + Integer.toString(d) + "(" + Integer.toString(s) +")";
        
        System.out.println (content);

       // buffFileWriter.write (content);
        ++emitLoc;
        // if( TraceCode )
        // { 
        //     content="\t" + c;
        //     buffFileWriter.write ( content);
        // }
        
        if( highEmitLoc < emitLoc )
        {
            highEmitLoc = emitLoc;
        }
    }

public static void emitRM_Abs( String op,int r, int a, String c ) 
{
    String content= Integer.toString(emitLoc) +": " + op +" " +Integer.toString(r) +"," + Integer.toString((a-(emitLoc+1))) + "("+ pc +") " + c;
    
    System.out.println (content);

    // buffFileWriter.write (content);
    ++emitLoc;
    // if( TraceCode )
    // { 
    //     content="\t" + c;
    //     buffFileWriter.write ( content);
    // }
    
    if( highEmitLoc < emitLoc )
    {
        highEmitLoc = emitLoc;
    }
}

    public static void start( DecList tree )
    {
        //Retrive flag option from Main arguments
        flagOption = Main.getFlag();

        if (flagOption == 'c') 
        {
            System.out.println( "\nCode Generation Start:\n" );
            genPreludeCode ();

            codeGen(tree);

            genFinaleCode();
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

        emitLoc += 11;
        System.out.println( "* End of standard prelude." );
    }


    static public void genFinaleCode()
    {
        System.out.println( "* Finale:" );       
        emitRM( "ST", fp, globalOffset+ofpFO, fp, "push ofp" );
        emitRM( "LDA", fp, globalOffset, fp, "push frame" );
        emitRM( "LDA", ac, 1, pc, "load ac with ret ptr" );
        emitRM_Abs( "LDA", pc, entry, "jump to main loc" );
        emitRM( "LD", fp, ofpFO, fp, "pop frame" );
        emitRO( "HALT", 0, 0, 0, "" );
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
    static public void codeGen(VarDecList tree) 
    {

        while( tree != null ) 
        {
            codeGen(tree.head);

            tree = tree.tail;
        } 
    }

    //VarDecList for normal lists
    static public void codeGen(VarDecList tree, String currentScope) 
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
            codeGen(backlist.get(i), currentScope);
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

    static public String codeGen(Var tree) 
    {
        if( tree instanceof SimpleVar )
        {
            return codeGen( (SimpleVar)tree );
        }
        else if( tree instanceof IndexVar )
        {
            String name=codeGen( (IndexVar)tree );
            return name;
        }
        return "";
    }

    static public void codeGen(Dec tree) 
    {
        //System.out.println( "Dec!");
        if (tree instanceof FunctionDec)
          codeGen( (FunctionDec)tree );
        else if (tree instanceof SimpleDec)
          codeGen( (SimpleDec)tree );
        else if (tree instanceof ArrayDec)
          codeGen( (ArrayDec)tree ); 
        else 
        {
          //System.out.println( "Illegal expression at line " + tree.pos  );
        }
    }

    static public void codeGen(Dec tree, String currentScope) 
    {
        if (tree instanceof FunctionDec)
          codeGen( (FunctionDec)tree );
        else if (tree instanceof SimpleDec)
          codeGen( (SimpleDec)tree, currentScope );
        else if (tree instanceof ArrayDec)
          codeGen( (ArrayDec)tree ); 
        else 
        {
          //System.out.println( "Illegal expression at line " + tree.pos  );
        }
    }

    static public void codeGen(NameTy tree) 
    {
        if (tree.typ == NameTy.INT) 
        {
           //System.out.print( "Int " );
        }
        else if (tree.typ == NameTy.VOID) 
        {
           //System.out.print( "Void " );
        }
    }

    static public void codeGen(CompoundExp tree) 
    {
        codeGen(tree.decs);
        codeGen(tree.exps);
    }

    static public void codeGen(CompoundExp tree, String currentScope) 
    {
       // System.out.println( "Comp exp " + currentScope);
        codeGen(tree.decs, currentScope);
        codeGen(tree.exps);
    }

    static public void codeGen(FunctionDec tree) 
    {
        //System.out.print("FunctionDec: Name: " + tree.func );


        int savedLoc = emitSkip(1);

        if (tree.func.equals("main")) 
        {
            entry = emitLoc;
            //System.out.println("entry: " + entry);
        }

        //Set scope to the current function
        currentScope = tree.func;

        emitRM("ST",ac,retFO,fp,"store return");

        //codeGen(tree.result);  
        codeGen(tree.params, tree.func);
        codeGen(tree.body, tree.func);  

        emitRM( "LD", pc, retFO, fp, "return to caller" );

        int savedLoc2 = emitSkip(0);
        emitBackup(savedLoc);
        emitRM_Abs("LDA",pc,savedLoc2,"");
        emitRestore();

        //At end of function set scope back to global
        currentScope = "Global";

    }

    static public String codeGen(IntExp tree) 
    {   
        //System.out.print( "IntExp: " + tree.value + " ");  
        return Integer.toString (tree.value);
    }


        static public void codeGen(SimpleDec tree)
    {
        //System.out.print( "Simple Dec: Type: ");
        codeGen(tree.typ);
        System.out.println( "*Storing var " + tree.name);
        //set the value of the offset in the symbol table
        //tree.offset = initFO; 
        initFO -= 1;
    }

    static public void codeGen(SimpleDec tree, String currentScope)
    {
        //System.out.print( "Simple Dec: Type: ");
        codeGen(tree.typ);
        System.out.println( "*Storing var " + tree.name +" in scope "+ currentScope);
        //set the value of the offset in the symbol table
        table.SetOffset(tree.name, currentScope ,initFO); 
        initFO -= 1;
    }

    static public void codeGen(ArrayDec tree) 
    {   
        //System.out.print( "Array Dec: Type: ");       
        codeGen(tree.typ);   
    }


    static public void codeGen(AssignExp tree) 
    {   
        //Left hand side will always be a variable
        String leftVar;
        int lhsOffset;
        
        //Retrive variable name
        leftVar = codeGen(tree.lhs);
        lhsOffset = table.getOffset(leftVar,currentScope);

        if( tree.rhs instanceof IntExp)
        {
            int rhsVal;
            rhsVal = ((IntExp)tree.rhs).value;
            System.out.println("*retrving var " + leftVar);
            emitRM("LDA",ac,lhsOffset,fp,"retrving var");
            emitRM("ST",ac,initFO,fp,"");
            emitRM("LDC",ac,rhsVal,ac,"");

            emitRM("LD",ac1,initFO,fp,"");
            emitRM("ST",ac,0,ac1,"");
            System.out.println("*stored " + rhsVal +" in "+ leftVar +" offset:"+lhsOffset);

        }
        else if( tree.rhs instanceof VarExp)
        {
            String rightVar;
            int rhsOffset;

            //get name of rhs var
            rightVar = codeGen((VarExp)tree.rhs);
            rhsOffset = table.getOffset(rightVar,currentScope);

            System.out.println("*setting var " + leftVar +" = "+rightVar);
            emitRM("LDA",ac,lhsOffset,fp,"retrving var");
            emitRM("ST",ac,initFO,fp,"");
            emitRM("LD",ac,rhsOffset,fp,"");

            emitRM("LD",ac1,initFO,fp,"");
            emitRM("ST",ac,0,ac1,"");
            System.out.println("*stored " + rightVar +" to "+ leftVar +" offset:"+lhsOffset);

        }
        else if( tree.rhs instanceof OpExp)
        {   
            //call genOpExp and then store value in ac register
            String rightVar;
            int rhsOffset;

            //get name of rhs var
            rightVar = codeGen((VarExp)tree.rhs);
            rhsOffset = table.getOffset(rightVar,currentScope);

            System.out.println("*setting var " + leftVar +" = "+rightVar);
            emitRM("LDA",ac,lhsOffset,fp,"retrving var");
            emitRM("ST",ac,initFO,fp,"");
            emitRM("LD",ac,rhsOffset,fp,"");

            emitRM("LD",ac1,initFO,fp,"");
            emitRM("ST",ac,0,ac1,"");
            System.out.println("*stored " + rightVar +" to "+ leftVar +" offset:"+lhsOffset);

        }


        codeGen(tree.rhs);
    }

    static public void codeGen(IfExp tree) 
    {

        //System.out.print( "IfExp:" );
        codeGen(tree.test);
        codeGen(tree.thenp);
        
        if (tree.elsep != null )
        {
            codeGen( tree.elsep);
        }
    }

    static public String  codeGen(IndexVar tree) 
    {

       // System.ouprint( "IndexVar:" + tree.name + " " );
        codeGen(tree.index);
        return tree.name;
    }

    static public void codeGen(WhileExp tree) 
    {
        //System.out.print("WhileExp:");

        codeGen(tree.test);
        codeGen(tree.body);
    }

    static public void codeGen(ReturnExp tree) 
    {

       // System.out.print( "ReturnExp: " );
        codeGen(tree.exp); 
    }

    static public String codeGen(SimpleVar tree) 
    {
       return tree.name;
       // System.out.print( "SimpleVar: " + tree.name + " ");
    }

    static public void codeGen(CallExp tree) 
    {
       
        //System.out.print( "CallExp: " + tree.func + " " ); 
        codeGen (tree.args);
    }

    static public void codeGen(OpExp tree) 
    {
        int rightOffset=-1000,leftOffset=-1000;

        switch( tree.op ) {
            case OpExp.PLUS:
                if (tree.left instanceof VarExp)
                {
                String varName= codeGen ((VarExp) tree.left);

                    leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof VarExp){
                String varName= codeGen ((VarExp) tree.right);

                 rightOffset=table.getOffset (varName,currentScope);

                }
                if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }

                if (rightOffset>-1000 && leftOffset>-1000){
                emitRM( "LD", ac, rightOffset, fp, "return to caller" );
                emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
                emitRO ( "ADD", ac, ac, ac1, "return to caller" );

                }



            
            break;
          case OpExp.MINUS:
        if (tree.left instanceof VarExp)
        {
            String varName= codeGen ((VarExp) tree.left);
         
                leftOffset=table.getOffset (varName,currentScope);

            
        }
         if (tree.right instanceof VarExp){
            String varName= codeGen ((VarExp) tree.right);
           
             rightOffset=table.getOffset (varName,currentScope);
            
        }
        if (tree.left instanceof IntExp)
            {
            String varName= codeGen ((IntExp) tree.left);

            leftOffset=table.getOffset (varName,currentScope);


            }
            if (tree.right instanceof IntExp){
            String varName= codeGen ((IntExp) tree.right);

            rightOffset=table.getOffset (varName,currentScope);

            }

        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }

            break;
          case OpExp.MUL:

        if (tree.left instanceof VarExp)
        {
            String varName= codeGen ((VarExp) tree.left);
         
                leftOffset=table.getOffset (varName,currentScope);

            
        }
         if (tree.right instanceof VarExp){
            String varName= codeGen ((VarExp) tree.right);
           
             rightOffset=table.getOffset (varName,currentScope);
            
        }
         if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }

        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "MUL", ac, ac, ac1, "return to caller" );


        }


            break;
          case OpExp.DIV:
        if (tree.left instanceof VarExp)
        {
            String varName= codeGen ((VarExp) tree.left);
         
                leftOffset=table.getOffset (varName,currentScope);

            
        }
         if (tree.right instanceof VarExp){
            String varName= codeGen ((VarExp) tree.right);
           
             rightOffset=table.getOffset (varName,currentScope);
            
        }
                        if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }

        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "DIV", ac, ac1, ac, "return to caller" );


        }

            break;

          case OpExp.EQ:
                 if (tree.left instanceof VarExp)
                {
                    String varName= codeGen ((VarExp) tree.left);
                 
                        leftOffset=table.getOffset (varName,currentScope);

                    
                }
                 if (tree.right instanceof VarExp){
                    String varName= codeGen ((VarExp) tree.right);
                   
                     rightOffset=table.getOffset (varName,currentScope);
                    
                }
                                if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }


        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }
                break;
          case OpExp.NE:
                 if (tree.left instanceof VarExp)
                {
                    String varName= codeGen ((VarExp) tree.left);
                 
                        leftOffset=table.getOffset (varName,currentScope);

                    
                }
                 if (tree.right instanceof VarExp){
                    String varName= codeGen ((VarExp) tree.right);
                   
                     rightOffset=table.getOffset (varName,currentScope);
                    
                }
                 if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }


        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }
                    break;
          case OpExp.LT:
             if (tree.left instanceof VarExp)
            {
                String varName= codeGen ((VarExp) tree.left);
             
                    leftOffset=table.getOffset (varName,currentScope);

                
            }
             if (tree.right instanceof VarExp){
                String varName= codeGen ((VarExp) tree.right);
               
                 rightOffset=table.getOffset (varName,currentScope);
                
            }
                            if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }


        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }
            break;
          case OpExp.LE:
             if (tree.left instanceof VarExp)
            {
                String varName= codeGen ((VarExp) tree.left);
             
                    leftOffset=table.getOffset (varName,currentScope);

                
            }
             if (tree.right instanceof VarExp){
                String varName= codeGen ((VarExp) tree.right);
               
                 rightOffset=table.getOffset (varName,currentScope);
                
            }
                            if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }


        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }
                break;
          case OpExp.GT:
             if (tree.left instanceof VarExp)
            {
                String varName= codeGen ((VarExp) tree.left);
             
                    leftOffset=table.getOffset (varName,currentScope);

                
            }
             if (tree.right instanceof VarExp){
                String varName= codeGen ((VarExp) tree.right);
               
                 rightOffset=table.getOffset (varName,currentScope);
                
            }
             if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }


        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }
                break;
          case OpExp.GE:
             if (tree.left instanceof VarExp)
            {
                String varName= codeGen ((VarExp) tree.left);
             
                    leftOffset=table.getOffset (varName,currentScope);

                
            }
             if (tree.right instanceof VarExp){
                String varName= codeGen ((VarExp) tree.right);
               
                 rightOffset=table.getOffset (varName,currentScope);
                
            }
                            if (tree.left instanceof IntExp)
                {
                String varName= codeGen ((IntExp) tree.left);

                leftOffset=table.getOffset (varName,currentScope);


                }
                if (tree.right instanceof IntExp){
                String varName= codeGen ((IntExp) tree.right);

                rightOffset=table.getOffset (varName,currentScope);

                }


        if (rightOffset>-1000 && leftOffset>-1000){
            emitRM( "LD", ac, rightOffset, fp, "return to caller" );
            emitRM( "LD", ac1, leftOffset, fp, "return to caller" );
            emitRO ( "SUB", ac, ac1, ac, "return to caller" );


        }
                break;
          default:
         }

        codeGen(tree.left);
        codeGen(tree.right); 
    }

    static public void codeGen(NilExp tree) 
    {
       // System.out.print( "Nil" );
    }

    static public String codeGen(VarExp tree)
    {
       return codeGen(tree.variable);
    }  
    
}

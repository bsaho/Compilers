package codeGen;

import absyn.*;
import cminus.Main;

public class CodeGen
{
    public static char flagOption;

    public static void start( DecList tree )
    {
        //Retrive flag option from Main arguments
        flagOption = Main.getFlag();

        if (flagOption == 'c') 
        {
            System.out.println( "\nCode Generation Start:\n" );
        }
       
    } 

    public static void main(String[] args) 
    {
    
    }
}

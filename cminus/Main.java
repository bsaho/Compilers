/*
  To Run: 
    java -classpath /usr/share/java/cup.jar:. cminus/Main 1.cm -a
*/
package cminus;

import java.io.*;
   
public class Main 
{
    static public char optionFlag = 'a';

    static public void main(String argv[]) 
    {    
        //Grab flag option if it exists
        if (argv.length > 1) 
        {
            optionFlag = argv[1].charAt(1);
        }
        /* Start the parser */
        try 
        {
            parser p = new parser(new Lexer(new FileReader(argv[0])));
            Object result = p.parse().value;      
        } 
        catch (Exception e) 
        {
            /* do cleanup here -- possibly rethrow e */
            e.printStackTrace();
        }
    }

    static public char getFlag()
    {
        return optionFlag;
    }
}



package absyn;
import java.io.*;
import java.util.*;

public class SymbolTable 
{

    class symbolListItem 
    {

        public String symbolName;
        public String symbolType;
        public int lineNo;
        public int arraySize;
        public int offset;
        public int numParams;

        public symbolListItem(String name, String type, int lineNum) 
        {
            symbolName = name;
            symbolType = type;
            lineNo= lineNum ;
            arraySize=0;
        }

        public symbolListItem(String name, String type,int size, int lineNum) 
        {
            symbolName = name;
            symbolType = type;
            lineNo= lineNum ;
            arraySize=size;
        }

        //For functions
        public symbolListItem(String name, String type,int numParams ,int lineNum, boolean sd) 
        {
            symbolName = name;
            symbolType = type;
            lineNo= lineNum ;
            arraySize=0;
           // System.out.println("numParams DEc"+ name+" "+ numParams);
            this.numParams = numParams;
        }
    }

    public class symbolList
    {
        public ArrayList <symbolListItem> contents;
        public String prevScope;

        public symbolList(String scope)
        {
            contents = new ArrayList <symbolListItem>();
            prevScope = scope;
        }
        public symbolList()
        {
            contents = new ArrayList <symbolListItem>();
            prevScope = "Global";
        }
    }

    HashMap<String, symbolList> table;
    String currentScope;
    String lastScopeAdded;

    //Constructor
    public SymbolTable()
    {
        table = new HashMap<String, symbolList >();
        table.put("Global", new symbolList());
        //table.addScope("output");
        currentScope="Global";
        lastScopeAdded=currentScope;
    }

    public int getNumParam(String varName)
    {
        String upperScope;

        int size = table.keySet().size ();
        Set scopeNames = table.keySet();
        ArrayList <symbolListItem> temp;
        Iterator index = scopeNames.iterator ();

        //Check for varName in higher scopes until Global scope is reached
        upperScope = table.get(currentScope).prevScope;
        // while(!upperScope.equals("Global"))
        // {   
        //     temp = table.get(upperScope).contents;
        //     for (int i=0;i<temp.size();i++)
        //     {
        //         if (varName.equals(temp.get(i).symbolName))
        //         {
        //             return temp.get(i).numParams;
        //         }
        //     }
        //     upperScope = table.get(upperScope).prevScope;
        // }

                //System.out.println ("Search string " + varName);

         //At end,check Global scope too
        temp = table.get("Global").contents;
        for (int i=0;i<temp.size();i++)
        {
            if (varName.equals(temp.get(i).symbolName))
            {
                     //                System.out.println ("Symbol " + varName + " Found! In restricted scope " + currentScope);
                     // System.out.println("numParams "+ temp.get(i).numParams);
                 return temp.get(i).numParams;
            }
        }

         return 0;
    }

    //Returns true if sucessful
    public boolean SetOffset(String varName, String scopeLevel,int newOffset)
    {
        Set scopeNames = table.keySet();
        ArrayList <symbolListItem> temp;
        Iterator index = scopeNames.iterator ();

        while (index.hasNext())
        {
            String searchString = (String) index.next();
            temp = table.get(searchString).contents;
            for (int i=0;i<temp.size();i++)
            {
                if (varName.equals(temp.get(i).symbolName))
                {
                    temp.get(i).offset = newOffset;
                    return true;
                }
            }

        }
        return false;
    }

    //Returns offset value if found and -10000 if not found
    public int getOffset(String varName, String scopeLevel)
    {
        Set scopeNames = table.keySet();
        ArrayList <symbolListItem> temp;
        Iterator index = scopeNames.iterator ();

        while (index.hasNext())
        {
            String searchString = (String) index.next();

            temp = table.get(searchString).contents;
            for (int i=0;i<temp.size();i++)
            {
                if (varName.equals(temp.get(i).symbolName))
                {
                    return temp.get(i).offset ;
                    
                }
            }

        }
        return -10000;
    }



    public String lookup(String valName, String choice, String choice2)
    {
        //System.out.println(table.keySet ().size ());
        //System.out.println(table.keySet());
            if (valName.length ()<1)
            {
                return "NULL";
            }

        int size = table.keySet().size ();
        Set scopeNames = table.keySet();
        ArrayList <symbolListItem> temp;
        Iterator index = scopeNames.iterator ();
        String upperScope;
        
        //System.out.println ("Search string " + valName);

        while (index.hasNext())
        {
            String searchString = (String) index.next();
            temp = table.get(searchString).contents;
            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    return temp.get(i).symbolType;
                }
            }

        }


        //Check for varName in higher scopes until Global scope is reached
        upperScope = table.get(currentScope).prevScope;
        while(!upperScope.equals("Global"))
        {   
            temp = table.get(upperScope).contents;
            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                    return temp.get(i).symbolType;
                }
            }
            upperScope = table.get(upperScope).prevScope;
        }
        //At end,check Global scope too
        temp = table.get("Global").contents;
        for (int i=0;i<temp.size();i++)
        {
            if (valName.equals(temp.get(i).symbolName))
            {
                //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                return temp.get(i).symbolType;
            }
        }

         return "NULL";
    }
    public String lookup(String valName,String choice)
    {
            ArrayList <symbolListItem> temp;
            String upperScope;

            temp = table.get(currentScope).contents;
            if (valName.length ()<1)
            {
                return "NULL";
            }

            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                    return temp.get(i).symbolType;
                }
            }

            if (choice.equals("2")) 
            {
                //Check for varName in higher scopes until Global scope is reached
                upperScope = table.get(currentScope).prevScope;
                while(!upperScope.equals("Global"))
                {   
                    temp = table.get(upperScope).contents;
                    for (int i=0;i<temp.size();i++)
                    {
                        if (valName.equals(temp.get(i).symbolName))
                        {
                            //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                            return temp.get(i).symbolType;
                        }
                    }
                    upperScope = table.get(upperScope).prevScope;
                }
                //At end,check Global scope too
                temp = table.get("Global").contents;
                for (int i=0;i<temp.size();i++)
                {
                    if (valName.equals(temp.get(i).symbolName))
                    {
                        //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                        return temp.get(i).symbolType;
                    }
                }
            }

            //System.out.println ("lookup " + valName);
            return "NULL";
        
    }

    //Lookup Method
    public boolean lookup(String valName,int choice)
    {
            ArrayList <symbolListItem> temp;
            String upperScope;

            if (valName.length ()<1)
            {
                return false;
            }

            temp = table.get(currentScope).contents;
            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                    return true;
                }
            }

            //Check in higher scopes 
            if (choice == 2) 
            {
                //Check for varName in higher scopes until Global scope is reached
                upperScope = table.get(currentScope).prevScope;
                while(!upperScope.equals("Global"))
                {   
                    temp = table.get(upperScope).contents;
                    for (int i=0;i<temp.size();i++)
                    {
                        if (valName.equals(temp.get(i).symbolName))
                        {
                            //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                            return true;
                        }
                    }
                    upperScope = table.get(upperScope).prevScope;
                }

                 //At end,check Global scope too
                temp = table.get("Global").contents;
                for (int i=0;i<temp.size();i++)
                {
                    if (valName.equals(temp.get(i).symbolName))
                    {
                        //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                        return true;
                    }
                }
            }
            
            //System.out.println ("lookup " + valName);
            return false;
        
    }

    //Lookup Method
    public boolean lookup(String valName)
    {
        //System.out.println(table.keySet ().size ());
        //System.out.println(table.keySet());

        String upperScope;

        if (valName.length ()<1)
        {
            return false;
        }

        int size = table.keySet().size ();
        Set scopeNames = table.keySet();
        ArrayList <symbolListItem> temp;
        Iterator index = scopeNames.iterator ();
       // System.out.println ("Search string " + valName);

        //Check for varName in higher scopes until Global scope is reached
        upperScope = table.get(currentScope).prevScope;
        while(!upperScope.equals("Global"))
        {   
            temp = table.get(upperScope).contents;
            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                    return true;
                }
            }
            upperScope = table.get(upperScope).prevScope;
        }

         //At end,check Global scope too
        temp = table.get("Global").contents;
        for (int i=0;i<temp.size();i++)
        {
            if (valName.equals(temp.get(i).symbolName))
            {
                //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                return true;
            }
        }

         return false;
    }

    //Insert Method for variable decs
    public void add(String varName, String varType, int lineNum)
    {
        ArrayList <symbolListItem> current;
        current = table.get(currentScope).contents;
        symbolListItem newSymbol = new symbolListItem (varName,varType,lineNum);
        current.add(newSymbol);  
       // printSymbol (newSymbol);
    }
    public  void add (String varName, String varType,int size, int lineNum)
    {
        ArrayList <symbolListItem> current;
        current = table.get(currentScope).contents;
        symbolListItem newSymbol = new symbolListItem (varName,varType,size, lineNum);
        current.add(newSymbol);  
       // printSymbol (newSymbol);
    }
    public  void add (String varName, String varType,int numParams, int lineNum, boolean sd)
    {
        ArrayList <symbolListItem> current;
        current = table.get(currentScope).contents;
        symbolListItem newSymbol = new symbolListItem (varName,varType,numParams, lineNum,sd);
         //System.out.println ("func added " + varName+" "+ numParams);
        current.add(newSymbol);  
       // printSymbol (newSymbol);
    }


    //Insert Method for new scopes
    public void addScope (String scopeName, String scopeType, int lineNum)
    {   
        //System.out.println ("\nAdding scope: " + scopeName);
        table.put(scopeName, new symbolList(currentScope));
        lastScopeAdded = currentScope;
        currentScope = scopeName;
        ArrayList <symbolListItem> current;
        current = table.get(currentScope).contents;
        symbolListItem newSymbol = new symbolListItem (scopeName,scopeType,lineNum);
        current.add (newSymbol);
    }

    public void printSymbol (symbolListItem list){
        if (list.arraySize==0){
        System.out.println ("Variable " + list.symbolName + " declared of type " + list.symbolType + " on line " + list.lineNo);
        }else {
                    System.out.println ("Variable " + list.symbolName + " declared of array type " + list.symbolType + " of size "
                        + list.arraySize + " on line " + list.lineNo );

        }

    }
    public void printAll (){
         int size = table.keySet().size ();
        Set scopeNames = table.keySet();
        ArrayList <symbolListItem> temp;
        Iterator index = scopeNames.iterator ();

        while (index.hasNext())
        {
            String searchString = (String) index.next();
            temp = table.get(searchString).contents;
            for (int i=0;i<temp.size();i++)
            {       
                    symbolListItem symbolTemp=temp.get(i);
                    System.out.println ("Symbol " + symbolTemp.symbolName + " Found! In scope " + searchString);
                
            }

        }



    }
    public void printsymbolListItem (ArrayList <symbolListItem> list)
    {
        int size= list.size ();
        System.out.println ("Total list size is: " + size);

        for (int i=0;i<size; i++)
        {
            symbolListItem temp;
            temp=list.get (i);
            System.out.println (temp.symbolName + " of type " + temp.symbolType);
        }
        
    }

    //Remove Method
    public void delete()
    {
        Set scopeNames = table.keySet();
        Iterator index = scopeNames.iterator ();
        String scopeName;

        //Look through each scope in the table
        while (index.hasNext())
        {
            scopeName = (String)index.next();
            if(currentScope.equals(scopeName) ) 
            {
                ArrayList <symbolListItem> tempItem=table.get (scopeName).contents;
                //Set the scope back to previous
                currentScope = table.get(scopeName).prevScope;
                table.remove(scopeName);
                break;
            }

        }
    }

    static public void main(String argv[]) 
    {    
        SymbolTable t = new SymbolTable();

        // t.table.put("1", new ArrayList());
        // t.table.put("2", new ArrayList());
        // t.table.put("3", new ArrayList());

       // ArrayList current;

        //current = t.table.get("Global");
       // symbolListItem newSymbol= new symbolListItem ();
        t.add("var", "int",1);
        t.add("lol", "String",5); 
        t.add("toops", "void",6);
       // t.printsymbolListItem (t.table.get("Global"));
        t.add("C00", "Function",1);

        t.addScope("IF", "Loop",5); 
        t.add("lol2", "String",5); 
        t.add("lol", "String",5); 

        t.add("Lola", "If-Block",6);
        t.add("toops", "void",6);

        //t.printsymbolListItem (t.table.get(t.currentScope));
        t.lookup ("lol",0);
        t.lookup ("toops");
        t.lookup ("Lola");






    }
}



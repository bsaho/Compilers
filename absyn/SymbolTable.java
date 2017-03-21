package absyn;
import java.io.*;
import java.util.*;

class SymbolTable 
{

    class symbolList 
    {
       public String symbolName;
       public String symbolType;
       public int lineNo;
       public int arraySize;

        public symbolList(String name, String type, int lineNum) 
        {
            symbolName = name;
            symbolType = type;
            lineNo= lineNum ;
            arraySize=0;
        }
        public symbolList(String name, String type,int size, int lineNum) 
        {
            symbolName = name;
            symbolType = type;
            lineNo= lineNum ;
            arraySize=size;
        }


    }

    HashMap<String, ArrayList> table;
    String currentScope;
    String lastScopeAdded;

    //Constructor
    public SymbolTable()
    {
        table = new HashMap<String, ArrayList >();
        table.put("Global", new ArrayList());
        currentScope="Global";
        lastScopeAdded=currentScope;
    }

    public String lookup(String valName,String choice){
            ArrayList <symbolList> temp;
            temp = table.get(currentScope);
            if (valName.length ()<1){
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
            //System.out.println ("lookup " + valName);
            return "NULL";
        
    }

    //Lookup Method
    public boolean lookup(String valName,int choice){
            ArrayList <symbolList> temp;
            if (valName.length ()<1){
                return false;
            }
            temp = table.get(currentScope);
            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    //System.out.println ("Symbol " + valName + " Found! In restricted scope " + currentScope);
                    return true;
                }
            }
            //System.out.println ("lookup " + valName);
            return false;
        
    }

    //Lookup Method
    public boolean lookup(String valName){
        //System.out.println(table.keySet ().size ());
        //System.out.println(table.keySet());
        if (valName.length ()<1){
                return false;
            }

        int size = table.keySet().size ();
        Set scopeNames = table.keySet();
        ArrayList <symbolList> temp;
        Iterator index = scopeNames.iterator ();
        System.out.println ("Search string " + valName);

        while (index.hasNext())
        {
            String searchString = (String) index.next();
            temp = table.get(searchString);
            for (int i=0;i<temp.size();i++)
            {
                if (valName.equals(temp.get(i).symbolName))
                {
                    return true;
                }
            }

        }
         return false;
    }

    //Insert Method for variable decs
    public void add(String varName, String varType, int lineNum)
    {
        ArrayList <symbolList> current;
        current = table.get(currentScope);
        symbolList newSymbol = new symbolList (varName,varType,lineNum);
        current.add(newSymbol);  
       // printSymbol (newSymbol);
    }
    public  void add (String varName, String varType,int size, int lineNum)
    {
        ArrayList <symbolList> current;
        current = table.get(currentScope);
        symbolList newSymbol = new symbolList (varName,varType,size, lineNum);
        current.add(newSymbol);  
       // printSymbol (newSymbol);
    }


    //Insert Method for new scopes
    public void addScope (String scopeName, String scopeType, int lineNum)
    {   

        table.put(scopeName, new ArrayList <symbolList>());
        currentScope = scopeName;
        ArrayList <symbolList> current;
        current = table.get(currentScope);
        symbolList newSymbol = new symbolList (scopeName,scopeType,lineNum);
        //printSymbol (newSymbol);
        current.add (newSymbol);
        lastScopeAdded = scopeName;



        
    }

    public void printSymbol (symbolList list){
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
        ArrayList <symbolList> temp;
        Iterator index = scopeNames.iterator ();

        while (index.hasNext())
        {
            String searchString = (String) index.next();
            temp = table.get(searchString);
            for (int i=0;i<temp.size();i++)
            {       
                    symbolList symbolTemp=temp.get(i);
                    System.out.println ("Symbol " + symbolTemp.symbolName + " Found! In scope " + searchString);
                
            }

        }



    }
    public void printSymbolList (ArrayList <symbolList> list)
    {
        int size= list.size ();
        System.out.println ("Total list size is: " + size);

        for (int i=0;i<size; i++)
        {
            symbolList temp;
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
            if(lastScopeAdded.equals(scopeName) ) 
            {
                //printSymbolList(table.get(scopeName));
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
       // symbolList newSymbol= new symbolList ();
        t.add("var", "int",1);
        t.add("lol", "String",5); 
        t.add("toops", "void",6);
       // t.printSymbolList (t.table.get("Global"));
        t.add("C00", "Function",1);

        t.addScope("IF", "Loop",5); 
        t.add("lol2", "String",5); 
        t.add("lol", "String",5); 

        t.add("Lola", "If-Block",6);
        t.add("toops", "void",6);

        //t.printSymbolList (t.table.get(t.currentScope));
        t.lookup ("lol",0);
        t.lookup ("toops");
        t.lookup ("Lola");






    }
}


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
    }


    //Lookup Method
    public void lookup(String valName)
    {
        System.out.println(table.keySet ().size ());
        System.out.println(table.keySet());
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
                if (valName.equals(temp.get(i).symbolName))
                {
                    System.out.println ("Symbol " + valName + " Found! In scope " + searchString);
                }
            }

        }
    }

    //Insert Method for variable decs
    public void add(String varName, String varType, int lineNum)
    {
        ArrayList <symbolList> current;
        current = table.get(currentScope);
       	symbolList newSymbol = new symbolList (varName,varType,lineNum);
        current.add(newSymbol);  
    }
    public  void add (String varName, String varType,int size, int lineNum)
    {
        ArrayList <symbolList> current;
        current = table.get(currentScope);
        symbolList newSymbol = new symbolList (varName,varType,size, lineNum);
        current.add(newSymbol);  
    }


    //Insert Method for new scopes
    public void addScope (String scopeName, String scopeType, int lineNum)
    {   

        table.put(scopeName, new ArrayList <symbolList>());
        currentScope = scopeName;
        ArrayList <symbolList> current;
        current = table.get(currentScope);
        symbolList newSymbol = new symbolList (scopeName,scopeType,lineNum);
        current.add (newSymbol);
        lastScopeAdded = scopeName;
        
    }

    public void printSymbolList (ArrayList <symbolList> list)
    {
	    int size= list.size ();
	    System.out.println ("Total list size is: " + size);

	    for (int i=0;i<size; i++)
	    {
	    	symbolList temp;
	        temp=list.get (i);
	        System.out.println (temp.symbolName);
	    }
	    System.out.println("");
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
        		printSymbolList(table.get(scopeName));
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

        t.delete();
        //t.printSymbolList (t.table.get(t.currentScope));
        t.lookup ("lol");
        t.lookup ("toops");
      	t.lookup ("Lola");






    }
}



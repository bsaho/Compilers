import java.io.*;
import java.util.*;

class SymbolTable 
{

    class symbolList {
       public String symbolName;
       public String symbolType;
       public int lineNo;

        public symbolList(String name, String type, int lineNum) {
            symbolName = name;
            symbolType = type;
            lineNo= lineNum ;
        }
    }

    HashMap<String, ArrayList> table;
    String currentScope;

    //Constructor
    public SymbolTable()
    {
        table = new HashMap<String, ArrayList >();
        table.put("Global", new ArrayList());
        
        currentScope="Global";

    }


    //Lookup Method
    public void lookup()
    {

    }

    //Insert Method for variable decs
    public void add(String varName, String varType, int lineNum)
    {
        ArrayList <symbolList> current;
        current= table.get(currentScope);
       symbolList newSymbol= new symbolList (varName,varType,lineNum);
        current.add (newSymbol);

        
    }

    //Insert Method for new scopes
    public void addScope (String scopeName, String scopeType, int lineNum)
    {   

        table.put(scopeName, new ArrayList <symbolList>());
        currentScope=scopeName;
        ArrayList <symbolList> current;
        current= table.get(currentScope);
        symbolList newSymbol= new symbolList (scopeName,scopeType,lineNum);
        current.add (newSymbol);
        
    }
    public void printSymbolList (ArrayList <symbolList> list){
     int size= list.size ();
     System.out.println ("Total list size is: " + size);

     for (int i=0;i<size; i++){
        symbolList temp;
        temp=list.get (i);
        System.out.println (temp.symbolName);
     }
    }

    //Remove Method
    public void delete()
    {

    }

    static public void main(String argv[]) 
    {    
        SymbolTable t = new SymbolTable();

        // t.table.put("1", new ArrayList());
        // t.table.put("2", new ArrayList());
        // t.table.put("3", new ArrayList());

        ArrayList current;

        current = t.table.get("Global");
       // symbolList newSymbol= new symbolList ();
        t.add("var", "int",1);
         t.add("lol", "String",5); 
         t.add("toops", "void",6);
         t.printSymbolList (t.table.get("Global"));
                 t.add("C00", "Function",1);
         t.addScope("IF", "Loop",5); 
                  t.add("lol2", "String",5); 

         t.add("Lola", "If-Block",6);
        t.printSymbolList (t.table.get(t.currentScope));




    }
}



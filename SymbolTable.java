import java.io.*;
import java.util.*;

class SymbolTable 
{
    HashMap<Integer, ArrayList> table;

    //Constructor
    public SymbolTable()
    {
        table = new HashMap<Integer, ArrayList >();
    }


    //Lookup Method
    public void lookup()
    {

    }

    //Insert Method
    public void add()
    {

    }

    //Remove Method
    public void delete()
    {

    }

    static public void main(String argv[]) 
    {    
        SymbolTable t = new SymbolTable();

        t.table.put(1, new ArrayList());
        t.table.put(2, new ArrayList());
        t.table.put(3, new ArrayList());

        ArrayList current;

        current = t.table.get(3);

        current.add("C00");
        current.add("B00");

        System.out.println("Output: " + t.table.get(3));

    }
}



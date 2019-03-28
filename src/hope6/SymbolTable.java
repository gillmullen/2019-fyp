import java.util.*;

public class SymbolTable {

   private Hashtable<String, String> st;
   private Hashtable<String, String> types;
   private Hashtable<String, String> arr;

   public SymbolTable() { // constructor
      st = new Hashtable<>();
      types = new Hashtable<>();
      arr = new Hashtable<>();
   }

   public void insert(String id, String type) { // inserts new identifier and its type into symbol table
      st.put(id, type);
   }

   public Boolean lookup(String id) { // checks if an identifier exists, returns true if yes and false if no
      if(st.containsKey(id)) {
         return true;
      }
      return false;
   }

   public void insertArray(String id, String size) { // records size of new arrays
      arr.put(id, size);
   }

   public void print() { // prints out symbol table
      System.out.println(st.toString());
   }

   public Boolean existsDuplicate() { // checks if there are duplicate identifiers within each scope
      Set<String> values = st.keySet();
      if(values.size() < st.size()) {
         return true;
      }
      return false;
   }

   public String getType(String id) { // gets type of identifier (int/bool/string)
      return st.get(id);
   }

   public String getSymbol(String id) {
      String type = getType(id);
      String size;
      if(type.equals("int")){
         return "i32";
      }
      else if(type.equals("string")) {
         return "i8*";
      }
      else if(type.equals("bool")) {
         return "i1";
      }
      else if(type.equals("int[")){
         size = arr.get(id);
         return "[" + size +" x i32]";
      }
      else {
         size = arr.get(id);
         return "[" + size + " x i8*]";
      }
   }

   public LinkedList<String> getVariables() { // get all variables and constants
      Object[] obArr = st.keySet().toArray();
      String[] list = Arrays.asList(obArr).toArray(new String[obArr.length]);
      LinkedList<String> variables = new LinkedList<String>();
      variables = new LinkedList<> (Arrays.asList(list));
      return variables;
   }

}

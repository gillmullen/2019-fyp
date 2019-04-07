import java.util.*;

public class SymbolTable {

   private Hashtable<String, LinkedList<String>> st;
   private Hashtable<String, String> types;
   private Hashtable<String, String> declTypes;

   public SymbolTable() { // constructor
      st = new Hashtable<>();
      st.put("global", new LinkedList<>());
      types = new Hashtable<>();
      declTypes = new Hashtable<>();
   }

   public void createNewScope(String scope) { // creates new linkedlist for a new scope (main, func, etc)
      st.put(scope, new LinkedList<>());
   }

   public void insert(String scope, String id, String type, String declType) { // inserts new identifier, and its type and declaration type (var/const/func), into symbol table within a specific scope
      if (checkScope(scope) == false) {
         createNewScope(scope);
      }
      LinkedList<String> currentScope = st.get(scope);
      currentScope.add(id);
      String key = id + scope;
      types.put(key, type);
      declTypes.put(key, declType);
   }

   public void insertFunc(String scope, String id, String type, String declType) { // inserts new identifier, and its type and declaration type (var/const/func), into symbol table within a specific scope
      LinkedList<String> currentScope = st.get(scope);
      currentScope.add(id);
      String key = id + scope;
      types.put(key, type);
      declTypes.put(key, declType);
   }

   public Boolean checkScope(String scope) {
      if(st.get(scope) != null) {
         return true;
      }
      return false;
   }

   public Boolean lookup(String id, String scope) { // checks if an identifier exists within a certain scope, returns true if yes and false if no or that scope does not exist
      if (st.get(scope) == null) {
         return false;
      }
      else if(st.get(scope).contains(id)) {
         return true;
      }
      return false;
   }

   public void print() { // prints out symbol table
      String currentScope;
      LinkedList<String> scope;
      String key;

      Enumeration e = st.keys();
      while (e.hasMoreElements()) { // while symbol table not empty
         currentScope = (String) e.nextElement();
         scope = st.get(currentScope);
         System.out.println("Scope: " + currentScope);
         for(int i = 0; i < scope.size(); i++) { // for each identifier within a scope
            key = scope.get(i) + currentScope;
            System.out.println("Declaration Type= " + declTypes.get(key) + ", Identifier= " + scope.get(i) + ", Variable/Return Type= " + types.get(key));
         }
         System.out.println();
      }
   }

   public Boolean existsDuplicate() { // checks if there are duplicate identifiers within each scope
      String currentScope;
      LinkedList<String> scope;
      String id;

      Enumeration e = st.keys();
      while (e.hasMoreElements()) { // while symbol table not empty
         currentScope = (String) e.nextElement();
         scope = st.get(currentScope);

         while(!scope.isEmpty()) { // for each identifier within a scope
            id = scope.pop();
            if (scope.contains(id)) {
               System.out.println("Error: " + id + " already defined in " + currentScope + "!");
               return true;
            }
         }
      }
      return false;
   }

   public String getType(String scope, String id) { // gets type of identifier (int/bool)
      return types.get(id + scope);
   }

   public String getSymbol(String scope, String id) {
      String type = types.get(id + scope);
      String mty;
      if(type.equals("integer")) {
         mty = "i32";
      }
      else if(type.equals("boolean")) {
         mty = "i1";
      }
      else {
         mty = "i8*";
      }
      return mty;
   }

   public LinkedList<String> getVariables() { // get all variables and constants
      LinkedList<String> variables = new LinkedList<String>();
      String key;
      String currentScope;
      LinkedList<String> scope;

      Enumeration e = st.keys();
      while (e.hasMoreElements()) { // while symbol table not empty
         currentScope = (String) e.nextElement();
         scope = st.get(currentScope);
         for(int i = 0; i < scope.size(); i++) { // for each identifier within a scope
            key = scope.get(i) + currentScope;
            if (!declTypes.get(key).equals("FUNC")) { // if variable
               variables.add(key);
            }
         }
      }
      return variables;
   }

   public LinkedList<String> getFunctions() { // get all declared functions
      LinkedList<String> functions = new LinkedList<String>();
      String key;
      String currentScope;
      LinkedList<String> scope;

      Enumeration e = st.keys();
      while (e.hasMoreElements()) { // while symbol table not empty
         currentScope = (String) e.nextElement();
         scope = st.get(currentScope);
         for(int i = 0; i < scope.size(); i++) { // for each identifier within a scope
            key = scope.get(i) + currentScope;
            if (declTypes.get(key).equals("FUNC")) { // if function
               functions.add(scope.get(i));
            }
         }
      }
      return functions;
   }

}

import java.util.*;

public class SymbolTable {

   private Hashtable<String, LinkedList<String>> st;
   private Hashtable<String, String> types;
   private Hashtable<String, String> declTypes;
   private Hashtable<String, String> arraySizes;

   public SymbolTable() { // constructor
      st = new Hashtable<>();
      st.put("global", new LinkedList<>());
      types = new Hashtable<>();
      declTypes = new Hashtable<>();
      arraySizes = new Hashtable<>();
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
      declTypes.put(id, declType);
   }

   public void insertFunc(String scope, String id, String type, String declType) { // inserts new identifier, and its type and declaration type (var/const/func), into symbol table within a specific scope
      LinkedList<String> currentScope = st.get(scope);
      currentScope.add(id);
      String key = id + scope;
      types.put(key, type);
      declTypes.put(id, declType);
   }

   public void insertArray(String scope, String id, String type, String declType, String size) {
      if (checkScope(scope) == false) {
         createNewScope(scope);
      }
      LinkedList<String> currentScope = st.get(scope);
      currentScope.add(id);
      String key = id + scope;
      type += "[]";
      types.put(key, type);
      arraySizes.put(key, size);
      declTypes.put(id, declType);
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
         if(!scope.isEmpty()) {
            System.out.println("Scope: " + currentScope);
            for(int i = 0; i < scope.size(); i++) { // for each identifier within a scope
               key = scope.get(i) + currentScope;
               System.out.println("Declaration Type= " + declTypes.get(scope.get(i)) + ", Identifier= " + scope.get(i) + ", Variable/Return Type= " + types.get(key));
            }
            System.out.println();
         }
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

   public String getType(String scope, String id) { // gets type of identifier (int/bool/str)
      return types.get(id + scope);
   }

   public String getDeclType(String id) { // gets type of identifier (func/var/param)
      return declTypes.get(id);
   }

   public String getSymbol(String scope, String id) {
      String type = types.get(id + scope);
      System.out.println(types.toString());
      String mty;
      if(type.equals("integer") || type.equals("int")) {
         mty = "i32";
      }
      else if(type.equals("boolean")) {
         mty = "i1";
      }
      else if(type.equals("string")) {
         mty = "i8*";
      }
      else if(type.equals("int[]")) {
         mty = "[" + arraySizes.get(id + scope) + " x i32]";
      }
      else {
         mty = "[" + arraySizes.get(id + scope) + " x i8*]";
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
            if (!declTypes.get(scope.get(i)).equals("FUNC")) { // if variable
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
            if (declTypes.get(scope.get(i)).equals("FUNC")) { // if function
               functions.add(scope.get(i));
            }
         }
      }
      return functions;
   }

}

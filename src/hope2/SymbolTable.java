import java.util.*;

public class SymbolTable {

   private LinkedList<String> st;

   public SymbolTable() {
      st = new LinkedList<String>();
   }

   public void insert(String id) {
      st.add(id);
   }

   public Boolean lookup(String id) {
      if(st.contains(id)) {
         return true;
      }
      return false;
   }

   public void print() {
      for(int i = 0; i < st.size(); i++) {
         System.out.println(st.get(i));
      }
   }

   public Boolean existsDuplicate() {
      // String value;
      // while(st.size() > 0) {
      //    value = st.pollFirst();
      //    if(st.contains(value)) {
      //       System.out.println("Error: " + value + " already defined!");
      //       return true;
      //    }
      // }
      // return false;
      LinkedList<String> copy = st;
      String id;
      for(int i = 0; i < st.size(); i++) {
         id = copy.remove();
         for(int j = 0; j < copy.size(); j++) {
            if(id.equals(copy.get(j))) {
               return true;
            }
         }
      }
      return false;
   }

   public LinkedList<String> getVariables() {
      return st;
   }

}

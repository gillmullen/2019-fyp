import java.io.BufferedWriter;
import java.util.ArrayList;

public class Context {
   BufferedWriter buffer;
   ArrayList<DeclaredStrings> strings = new ArrayList<DeclaredStrings>();

   public Context(BufferedWriter b, ArrayList<DeclaredStrings> s) {
      this.buffer = b;
      this.strings = s;
   }
}

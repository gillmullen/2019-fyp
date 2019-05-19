import org.junit.*;
import static org.junit.Assert.*;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class HopefulTest {

   @Test
   public void testPrint() {
      try {
         String command = "java -jar HOPEFUL.jar testPrint.hope";
         Process process = Runtime.getRuntime().exec(command);
         BufferedReader br = new BufferedReader(
         new InputStreamReader(process.getInputStream()));
         String line, terminalText;
         while ((line = br.readLine()) != null) {
            terminalText += line + "\n";
         }
      }
      catch (IOException e) {
         System.out.println("Failed to execute test program");
         e.printStackTrace(System.out);
      }

      assertTrue(terminalText.equals("hello world\n"));
   }

   @Test
   public void testInt() {
      try {
         String command = "java -jar HOPEFUL.jar testInt.hope";
         Process process = Runtime.getRuntime().exec(command);
         BufferedReader br = new BufferedReader(
         new InputStreamReader(process.getInputStream()));
         String line, terminalText;
         while ((line = br.readLine()) != null) {
            terminalText += line + "\n";
         }
      }
      catch (IOException e) {
         System.out.println("Failed to execute test program");
         e.printStackTrace(System.out);
      }

      assertTrue(terminalText.equals("16\n"));
   }

   @Test
   public void testCondition() {
      try {
         String command = "java -jar HOPEFUL.jar testCondition.hope";
         Process process = Runtime.getRuntime().exec(command);
         BufferedReader br = new BufferedReader(
         new InputStreamReader(process.getInputStream()));
         String line, terminalText;
         while ((line = br.readLine()) != null) {
            terminalText += line + "\n";
         }
      }
      catch (IOException e) {
         System.out.println("Failed to execute test program");
         e.printStackTrace(System.out);
      }

      assertTrue(terminalText.equals("pass\n"));
   }

   @Test
   public void testWhile() {
      try {
         String command = "java -jar HOPEFUL.jar testWhile.hope";
         Process process = Runtime.getRuntime().exec(command);
         BufferedReader br = new BufferedReader(
         new InputStreamReader(process.getInputStream()));
         String line, terminalText;
         while ((line = br.readLine()) != null) {
            terminalText += line + "\n";
         }
      }
      catch (IOException e) {
         System.out.println("Failed to execute test program");
         e.printStackTrace(System.out);
      }

      assertTrue(terminalText.equals("1\n4\n9\n16\n25\n"));
   }

   @Test
   public void testFunc() {
      try {
         String command = "java -jar HOPEFUL.jar testFunc.hope";
         Process process = Runtime.getRuntime().exec(command);
         BufferedReader br = new BufferedReader(
         new InputStreamReader(process.getInputStream()));
         String line, terminalText;
         while ((line = br.readLine()) != null) {
            terminalText += line + "\n";
         }
      }
      catch (IOException e) {
         System.out.println("Failed to execute test program");
         e.printStackTrace(System.out);
      }

      assertTrue(terminalText.equals("4\n"));
   }

   public static void main(String args[]) {
      org.junit.runner.JUnitCore.main("junitfaq.HopefulTest");
   }

}

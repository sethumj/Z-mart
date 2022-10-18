package utilities;

import java.util.InputMismatchException;
import java.util.Scanner;

// Input class for get all types of input and all the errors in the input in this class itself

public class Input {
   private static Scanner sc = new Scanner(System.in);
   private static boolean checkPreviousInt = false;
    public static int getInteger(){
        checkPreviousInt = true;
        while(true) {
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print(PrintStatements.inputError);
            }
        }
    }
    public static String getString() {
        while (true) {
            if(checkPreviousInt) sc.nextLine();
            try {
                checkPreviousInt = false;
                return sc.nextLine();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print(PrintStatements.inputError);
            }
        }
    }
    public static float getFloat(){
        checkPreviousInt = true;
        while(true){
            try{
                return sc.nextFloat();
            }
            catch(InputMismatchException e){
                sc.nextLine();
                System.out.print(PrintStatements.inputError);
            }
        }
    }
    public static long getLong(){
        checkPreviousInt = true;
        while(true) {
            try {
                return sc.nextLong();
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.print(PrintStatements.inputError);
            }
        }
    }
}


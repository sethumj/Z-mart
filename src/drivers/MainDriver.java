package drivers;

import utilities.Input;
import utilities.PrintStatements;

public class MainDriver {
    public static void selectUserType(){
        Driver d = null;
        while(true) {
            System.out.println(PrintStatements.mainOptions);
            switch(Input.getInteger()){
                case 1:d = AdminDriver.getInstance();
                    break;
                case 2:d = UserDriver.getInstance();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(PrintStatements.inputError);
                    break;
            }
            if(d!=null)d.startDriver();
        }
    }
}

package admin;

import user.UserDriver;
import utilities.Input;
import utilities.PrintStatements;

public class MainDriver {
    public static void selectUserType(){
        Driver driver = null;
        while(true) {
            System.out.println(PrintStatements.MAIN_OPTIONS);
            switch(Input.getInteger()){
                case 1:driver = AdminDriver.getInstance();
                    break;
                case 2:driver = UserDriver.getInstance();
                    break;
                case 3:
                    return;
                default:
                    System.out.println(PrintStatements.INPUT_ERROR);
                    break;
            }
            if(driver!=null)driver.startDriver();
        }
    }
}

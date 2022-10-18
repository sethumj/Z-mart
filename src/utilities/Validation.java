package utilities;

public class Validation {
    public static boolean checkMobileNo(long mobileNo){
        if(mobileNo>=1000000000L && mobileNo<=9999999999L){
            return true;
        }
        else{
            return false;
        }
    }
}

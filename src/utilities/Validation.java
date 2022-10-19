package utilities;

public class Validation {
    public static boolean checkMobileNo(long mobileNo){
        return mobileNo >= 1000000000L && mobileNo <= 9999999999L;
    }
    public static boolean checkPincode(int pincode){
        return pincode >= 100000 && pincode <= 999999;
    }
}

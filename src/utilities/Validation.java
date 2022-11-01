package utilities;

public class Validation {
    public static boolean checkMobileNo(long mobileNo){
        return mobileNo >= 1000000000L && mobileNo <= 9999999999L;
    }
    public static boolean checkPincode(int pincode){
        return pincode >= 100000 && pincode <= 999999;
    }
    public static int getPincode(){
        while(true){
            int temp = Input.getInteger();
            if(Validation.checkPincode(temp)) return temp;
            else{
                System.out.println(PrintStatements.INVALID_PINCODE);
                System.out.println(PrintStatements.JUST_TRY_AGAIN);
            }
        }
    }
    public static long getMobileNo(){
        while(true){
            long temp = Input.getLong();
            if(Validation.checkMobileNo(temp)) return temp;
            else{
                System.out.println(PrintStatements.INVALID_MOBILE_NO);
                System.out.println(PrintStatements.JUST_TRY_AGAIN);
            }
        }
    }
    public static int rangeOfHundred(){
        while(true){
            int temp = Input.getInteger();
            if(temp>=0 && temp<100) return temp;
            else{
                System.out.println(PrintStatements.INPUT_ERROR);
                System.out.println(PrintStatements.JUST_TRY_AGAIN);
            }
        }
    }

    public static int getPositiveInt(){
        while(true) {
            int temp = Input.getInteger();
            if(temp>0) return temp;
            else {
                System.out.println(PrintStatements.ENTER_POSITIVE_NUMBER);
            }
        }
    }
    public static float getPositiveFloat(){
        while(true) {
            float temp = Input.getFloat();
            if(temp>0) return temp;
            else {
                System.out.println(PrintStatements.ENTER_POSITIVE_NUMBER);
            }
        }
    }
    public static int getPositiveIntOrZero(){
        while(true) {
            int temp = Input.getInteger();
            if(temp>=0) return temp;
            else {
                System.out.println(PrintStatements.ENTER_POSITIVE_NUMBER);
            }
        }
    }
    public static String getNonNullString(){
        while(true){
            String temp = Input.getString();
            if(temp.equals("")) System.out.println(PrintStatements.ENTER_VALID_NAME);
            else return temp;
        }
    }
}

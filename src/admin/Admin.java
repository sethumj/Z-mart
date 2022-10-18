package admin;

public class Admin {
     private static final String email = "a";
     private static final String password = "a";
    static String superMarketName = "Z-mart";
    public static boolean validateMail(String email){
        return email.equals(Admin.email);
    }
    public static boolean validatePassword(String password){
        return password.equals(Admin.password);
    }


}

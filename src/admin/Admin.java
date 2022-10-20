package admin;

public class Admin {
     private static final String email = "admin@zohocorp.com";
     private static final String password = "pass";
     public static boolean validateMail(String email){
        return email.equals(Admin.email);
    }
     public static boolean validatePassword(String password){
        return password.equals(Admin.password);
    }

}

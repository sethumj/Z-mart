package user;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {
    static UserAuthentication userAuthentication = new UserAuthentication();
    private UserAuthentication(){}
    public static UserAuthentication getInstance(){
        return userAuthentication;
    }
    private final HashMap<Long,String> userDetails = new HashMap<>();
    void addUser(Long mobileNo,String password){
        userDetails.put(mobileNo,password);
    }

    boolean userCheck(long mobileNo,String password) {
        if(userDetails.get(mobileNo) != null) {
            return userDetails.get(mobileNo).equals(password);
        }
        return false;
    }
    boolean checkAlreadyExist(long mobileNo){
        return userDetails.get(mobileNo) != null;
    }
}

package user;

import java.util.HashMap;
import java.util.Map;

public class UserAuthentication {
    static UserAuthentication userAuthentication = new UserAuthentication();
    private UserAuthentication(){}
    public static UserAuthentication getInstance(){
        return userAuthentication;
    }
    private HashMap<Long,String> userDetails = new HashMap<Long, String>();
    public void addUser(Long mobileNo,String password){
        userDetails.put(mobileNo,password);
    }

    public boolean userCheck(long mobileNo,String password) {
        for(Map.Entry<Long,String> entry : userDetails.entrySet()){
            if(entry.getKey() == mobileNo && entry.getValue().equals(password)) return true;
        }
        return false;
    }
    public boolean checkAlreadyExist(long mobileNo){
        for(Map.Entry<Long,String> entry : userDetails.entrySet()){
            if(entry.getKey() == mobileNo) return true;
        }
        return false;
    }
}

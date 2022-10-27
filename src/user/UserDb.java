package user;

import java.util.ArrayList;
import java.util.HashMap;

public class UserDb {
    private static final UserDb userDb = new UserDb();
    private static int userId = 8000;
    private UserDb(){}
    public static UserDb getInstance(){
        return userDb;
    }
    ArrayList<User> users = new ArrayList<>();
    HashMap<Integer,Integer> userOrderMap =new HashMap<>();
    User addUser(User newUser){
        userDb.users.add(newUser);
        return newUser;
    }
    int getUserId(){
        userId++;
        return userId;
    }
    void addOrder(int orderId,int userId){
        userDb.userOrderMap.put(orderId,userId);
    }
    public void orderReady(int orderId){
        int userId = userDb.userOrderMap.get(orderId);
        for(int i=0;i<userDb.users.size();i++){
            if(userDb.users.get(i).getUserId() == userId){
                userDb.users.get(i).history.put(orderId,(userDb.users.get(i).userOrders.remove(orderId)));
            }
        }
    }
}

package user;

import java.util.ArrayList;

public class UserDb {
    private static UserDb userDb = new UserDb();
    private UserDb(){}
    public static UserDb getInstance(){
        return userDb;
    }
    public ArrayList<User> users = new ArrayList<User>();
    public static User addUser(User user){
        userDb.users.add(user);
        return user;
    }
}

package user;

import java.util.ArrayList;

public class UserDb {
    private static final UserDb userDb = new UserDb();
    private UserDb(){}
    public static UserDb getInstance(){
        return userDb;
    }
    public ArrayList<User> users = new ArrayList<>();
    public static User addUser(User user){
        userDb.users.add(user);
        return user;
    }
}

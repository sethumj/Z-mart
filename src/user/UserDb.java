package user;

import java.util.ArrayList;

public class UserDb {
    public static ArrayList<User> users = new ArrayList<User>();
    public static User addUser(User user){
        users.add(user);
        return user;
    }
}

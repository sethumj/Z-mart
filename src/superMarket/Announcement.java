package superMarket;

import java.util.ArrayList;

public class Announcement {
    public static ArrayList<String> announcement = new ArrayList<String>();
    public static boolean addAnnouncement(String message){
        announcement.add(message);
        return true;
    }
    public static void viewAnnouncements(){
        for(int i=0;i<announcement.size();i++) System.out.println(i+1+"."+announcement.get(i));
    }
    public static boolean removeAnnouncement(int index){
        if(index>announcement.size()) return false;
        announcement.remove(index-1);
        return true;
    }
}

package superMarket;

import java.util.ArrayList;

public class Announcement {
    private Announcement(){}
    private static final Announcement announcement = new Announcement();
    public static Announcement getInstance(){
        return announcement;
    }
    private final ArrayList<String> announcementDb = new ArrayList<>();
    public boolean addAnnouncement(String message){
            announcement.announcementDb.add(message);
            return true;
    }
    public void viewAnnouncements(){
        for(int i=0;i<announcement.announcementDb.size();i++) System.out.println(i+1+"."+announcement.announcementDb.get(i));
    }
    public boolean removeAnnouncement(int index){
        if(index>announcement.announcementDb.size()) return false;
        announcement.announcementDb.remove(index-1);
        return true;
    }
}

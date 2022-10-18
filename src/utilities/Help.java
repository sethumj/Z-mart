package utilities;

import java.util.ArrayList;

public class Help {
    public static ArrayList<String> help = new ArrayList<String>();
    public static boolean addHelp(String message){
        help.add(message);
        return true;
    }
    public static void viewHelps(){
        for(int i=0;i<help.size();i++) System.out.println(i+1+"."+help.get(i));
    }
    public static boolean removeHelp(int index){
        if(index>help.size()) return false;
        help.remove(index-1);
        return true;
    }
}

package utilities;

import java.util.ArrayList;

public class Help {
    private Help(){}
    private static final Help help = new Help();
    public static Help getInstance(){
        return help;
    }
    private final ArrayList<String> helpDb = new ArrayList<>();
    public boolean addHelp(String message){
        help.helpDb.add(message);
        return true;
    }
    public void viewHelps(){
        for(int i=0;i<help.helpDb.size();i++) System.out.println(i+1+"."+help.helpDb.get(i));
    }
    public boolean removeHelp(int index){
        if(index>help.helpDb.size()) return false;
        help.helpDb.remove(index-1);
        return true;
    }
}

package admin;

import java.util.ArrayList;

public class Help {
    private Help(){}
    private static final Help help = new Help();
    public static Help getInstance(){
        return help;
    }
    private final ArrayList<String> helpDb = new ArrayList<>();

    ArrayList<String> getUserQuestions() {
        return userQuestions;
    }

    private final ArrayList<String> userQuestions = new ArrayList<>();
    boolean addHelp(String message){
        help.helpDb.add(message);
        return true;
    }
    public void viewHelps(){
        for(int i=0;i<help.helpDb.size();i++) System.out.println(i+1+"."+help.helpDb.get(i));
        System.out.println();
        System.out.println();
    }
    boolean removeHelp(int index){
        if(index>help.helpDb.size()) return false;
        help.helpDb.remove(index-1);
        return true;
    }
    void viewUserQuestions(){
        int count = 1;
        for(String question : userQuestions){
            System.out.println(count+"."+question);
            count++;
        }
    }
    public void addUserQuestions(String question){
        userQuestions.add(question);
    }
}

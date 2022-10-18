package superMarket;

import java.util.ArrayList;
public class PincodesDb {
    public static ArrayList<Integer> pincodes = new ArrayList<Integer>();
    public static void addPincode(int pin){
        pincodes.add(pin);
    }
    public static boolean checkPincode(int pin){
        for(int i = 0;i<pincodes.size();i++){
            if(pin == pincodes.get(i))return true;
        }
        return false;
    }
}

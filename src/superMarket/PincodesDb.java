package superMarket;

import java.util.ArrayList;
public class PincodesDb {
    private static PincodesDb pincodesDb = new PincodesDb();
    private PincodesDb(){}
    public static PincodesDb getInstance(){
        return pincodesDb;
    }
    private static ArrayList<Integer> pincodes = new ArrayList<Integer>();

    public static ArrayList<Integer> getPincodes() {
        return pincodesDb.pincodes;
    }

    public void addPincode(int pin){
        pincodes.add(pin);
    }
    public boolean checkPincode(int pin){
        for(int i = 0;i<pincodes.size();i++){
            if(pin == pincodes.get(i))return true;
        }
        return false;
    }
}

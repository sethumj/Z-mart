package superMarket;

import java.util.ArrayList;
public class PincodesDb {
    private static final PincodesDb pincodesDb = new PincodesDb();
    private PincodesDb(){}
    public static PincodesDb getInstance(){
        return pincodesDb;
    }
    private final ArrayList<Integer> pincodes = new ArrayList<>();

    public ArrayList<Integer> getPincodes() {
        return pincodesDb.pincodes;
    }

    public void addPincode(int pin){
        pincodes.add(pin);
    }
    public boolean checkPincode(int pin){
        for (Integer pincode : pincodes) {
            if (pin == pincode) return true;
        }
        return false;
    }
}

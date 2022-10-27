package admin;

import utilities.PrintStatements;

import java.util.ArrayList;
public class PincodesDb {
    private static final PincodesDb pincodesDb = new PincodesDb();
    private PincodesDb(){}
    public static PincodesDb getInstance(){
        return pincodesDb;
    }
    private final ArrayList<Integer> pincodes = new ArrayList<>();

    ArrayList<Integer> getPincodes() {
        return pincodesDb.pincodes;
    }

    void addPincode(int newPincode){
        for(int i=0;i<pincodesDb.pincodes.size();i++){
            if(newPincode == pincodesDb.pincodes.get(i)) {
                System.out.println(PrintStatements.PINCODE_ALREADY_EXIST);
                return;
            }
        }
        pincodes.add(newPincode);
    }
    public boolean checkPincode(int newPincode){
        for (Integer previousPincode : pincodes) {
            if (newPincode == previousPincode) return true;
        }
        return false;
    }
}

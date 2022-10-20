package user;

import superMarket.PincodesDb;
import utilities.Input;
import utilities.PrintStatements;

public class Address {
    private String  houseNo;
    private String street;
    private String area;
    private String district ;
    private int pincode ;

    Address(String houseNo, String street,String area,String district ,int pincode){
        this.houseNo = houseNo;
        this.street = street;
        this.area = area;
        this.district = district;
        this.pincode = pincode;
    }
    private static PincodesDb pincodesDb = PincodesDb.getInstance();
    public static Address getAddress(){
        System.out.println(PrintStatements.getAddress);
        System.out.println(PrintStatements.addressHouseNo);
        String houseNo = Input.getString();
        System.out.println(PrintStatements.addressStreet);
        String street = Input.getString();
        System.out.println(PrintStatements.addressArea);
        String area = Input.getString();
        System.out.println(PrintStatements.addressDistrict);
        String district = Input.getString();
        System.out.println(PrintStatements.addressPincode);
        int pincode;
        while(true) {
            pincode =Input.getInteger();
            if (pincodesDb.checkPincode(pincode)) {
                break;
            } else {
                System.out.println(PrintStatements.notServiceable);
                System.out.println(PrintStatements.justTryAgain);
            }
        }
        return new Address(houseNo,street,area,district,pincode);
    }
}

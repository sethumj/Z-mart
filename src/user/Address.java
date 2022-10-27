package user;

import admin.PincodesDb;
import utilities.Input;
import utilities.PrintStatements;
import utilities.Validation;

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
        System.out.println(PrintStatements.GET_ADDRESS);
        System.out.println(PrintStatements.ADDRESS_HOUSE_NO);
        String houseNo = Input.getString();
        System.out.println(PrintStatements.ADDRESS_STREET);
        String street = Input.getString();
        System.out.println(PrintStatements.ADDRESS_AREA);
        String area = Input.getString();
        System.out.println(PrintStatements.ADDRESS_DISTRICT);
        String district = Input.getString();
        System.out.println(PrintStatements.ADDRESS_PINCODE);
        int pincode;
        while(true) {
            pincode = Validation.getPincode();
            if (pincodesDb.checkPincode(pincode)) {
                break;
            } else {
                System.out.println(PrintStatements.NOT_SERVICEABLE);
                System.out.println(PrintStatements.JUST_TRY_AGAIN);
            }
        }
        return new Address(houseNo,street,area,district,pincode);
    }
}

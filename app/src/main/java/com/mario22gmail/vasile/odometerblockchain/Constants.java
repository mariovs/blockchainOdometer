package com.mario22gmail.vasile.odometerblockchain;

public final class Constants {
    public static String CarManufacturer= "manufacturer";
    public static String Model = "model";
    public static String ProductionYear = "productionYear";
    public static String engineSize = "engineSize";
    public static String enginePower = "enginePower";
    public static String currentOwner = "ownerName";
    public static String VehicleIdentificationNumber = "carId";
    public static String Mileage = "millage";
    public static String Consumption = "consumption";
    public static String MeterUnit = "meterUnit";
    public static String SerialNumber = "serialNumber";
    public static String Seller = "seller";
    public static String DateOfSale = "dateOfSale";
    public static String OriginatorSignature = "originatorSignature";

    //used to pass asset id to another activity
    public static String assetIdConstant = "assetIdKeyToSent";
    public static String publicKeyConstant = "publicKeyConstant";
    public static String privateKeyConstant = "privateKeyConstant";

    public enum AssetStatus{
        Active,
        Transferred
    }


    public static String LogKey = "MMM_OOO";
}

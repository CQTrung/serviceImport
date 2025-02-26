package org.example.enums;

public enum DeliveryOrderImportFile {
    DELIVERY_STATUS(0, "Delivery Status"),
    COMPANY_ID(1, "Company Id"),
    DOCUMENT_TYPE(2, "Document Type"),
    CALCULATION_ORDER(3, "Calculation Order"),
    DELIVERY_TYPE(4, "Delivery Type"),
    BRANCH_ID(5, "Branch Id"),
    SENDER_ID(6, "Sender Id"),
    SENDER_NAME(7, "Sender Name"),
    PICKUP_ID(8, "Pickup Id"),
    PICKUP_POINT(9, "Pickup Point"),
    PICKUP_TIME(10, "Pickup Time"),
    SENDER_PHONE(11, "Sender Phone"),
    TYPE_OF_GOOD(12, "Type Of Good"),
    PICKUP_LATITUDE(13, "Pickup Latitude"),
    PICKUP_LONGITUDE(14, "Pickup Longtitude"),
    PICKUP_NOTE(15, "Pickup Note"),
    INVOICE_NO(16, "Invoice No"),
    SALE_ORDER_NO(17, "Sale Order No"),
    DOCUMENT_NO(18, "Document No"),
    PRODUCT_NO(19, "Product No"),
    REFERENCE_NO(20, "Reference No"),
    INVOICE_DATE(21, "Invoice Date"),
    DELIVERY_ID(22, "Delivery Id"),
    RECEIVER_NAME(23, "Receiver Name"),
    DELIVERY_POINT(24, "Delivery Point"),
    AREA_MASTER_ID(25, "Area Master Id"),
    DUE_DATE(26, "Due Date"),
    RECEIVER_PHONE(27, "Receiver Phone"),
    UNIT(28, "Unit"),
    DELIVERY_LATITUDE(29, "Delivery Latitude"),
    DELIVERY_LONGITUDE(30, "Delivery Longtitude"),
    DELIVERY_NOTE(31, "Delivery Note"),
    COD(32, "Cod"),
    LOADING_BOX(33, "Loading Box"),
    LOADING_WEIGHT(34, "Loading Weight"),
    LOADING_LITER(35, "Loading Liter"),
    LOADING_CBM(36, "Loading Cbm"),
    LOADING_TARGET(37, "Loading Target"),
    SS(38, "Ss"),
    S(39, "S"),
    M(40, "M"),
    L(41, "L"),
    XL(42, "Xl"),
    XXL(43, "Xxl"),
    XXXL(44, "Xxxl"),
    XXXXL(45, "Xxxxl"),
    XXXXXL(46, "Xxxxxl"),
    PRODUCT_SIZE(47, "Product Size"),
    SHIPMENT_ID(48, "Shipment Id"),
    SHIPMENT_TYPE(49, "Shipment Type"),
    SHIPMENT_AREA(50, "Shipment Area");

    private final int key;
    private final String value;

    DeliveryOrderImportFile(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(int key) {
        for (DeliveryOrderImportFile item : DeliveryOrderImportFile.values()) {
            if (item.getKey() == key) {
                return item.getValue();
            }
        }
        return null; // or throw an exception
    }

}
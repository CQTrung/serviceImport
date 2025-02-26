package org.example.DTO;




import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliveryOrderRequest {
    private Integer deliveryOrderId;
    private String deliveryStatus;
    private String companyId;
    private Integer documentType;
    private String calculationOrder;
    private Integer deliveryType;
    private String branchId;
    private String senderId;
    private String senderName;
    private String senderPhone;
    private String pickupId;
    private String pickupPoint;
    private String pickupTime;
    private Double pickupLatitude;
    private Double pickupLongtitude;
    private String pickupNote;
    private String typeOfGood;
    private String invoiceNo;
    private String invoiceDate;
    private String saleOrderNo;
    private String documentNo;
    private String productNo;
    private String referenceNo;
    private String deliveryId;
    private String deliveryPoint;
    private Double deliveryLatitude;
    private Double deliveryLongtitude;
    private String deliveryNote;
    private String receiverName;
    private String receiverPhone;
    private Integer unit;
    private String areaMasterId;
    private String dueDate;
    private String cod;
    private Integer loadingBox;
    private Double loadingWeight;
    private Double loadingLiter;
    private Double loadingCbm;
    private Double loadingTarget;
    private Integer ss;
    private Integer s;
    private Integer m;
    private Integer l;
    private Integer xl;
    private Integer xxl;
    private Integer xxxl;
    private Integer xxxxl;
    private Integer xxxxxl;
    private String productSize;
    private String shipmentId;
    private String shipmentType;
    private String shipmentArea;
    private Integer status;
    private Integer deliveryType_2;
    private Integer documentType_2;
    private Double loadingBoxNoSize;
    private Integer loadingPiece;
    private String otherNumber;
    private String shipTo;
    private String ss5xl;
    private Double special;
    private Double amountOfMoney;
    private Integer splited;
    private String driverPhone;
    private Double specialSize;

    private String statusType;
    private String orderDate;
    private String invoiceReceivingDate;
    private String areaOfResponsibility;
    private String region;
    private Double pallet;
    private Double insurance;
    private String deliveryNote_2;
    private String deliveryNote_3;
    private String deliveryNote_4;

    private String province;
    private String district;
    private String subDistrict;
    private String originShipment;
}

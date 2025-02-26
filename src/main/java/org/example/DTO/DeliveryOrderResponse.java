package org.example.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.ALWAYS)
public class DeliveryOrderResponse  {
    private Integer status;
    private String invoiceNo;
    private String senderName;
    private String pickupId;
    private String pickupTime;
    private String receiverName;
    private String deliveryPoint;
    private String dueDate;
    private String statusType;
    private String deliveryStatus;
    private Integer loadingBox;
    private Double loadingWeight;

    private String id;
    private Integer deliveryOrderId;
    private String companyId;

    //mapper id to name 
    private Integer documentType;
    private String documentTypeStr_en;
    private String documentTypeStr_th;

    private Integer documentType_2;
    private String documentType_2Str_en;
    private String documentType_2Str_th;

    private Integer deliveryType;
    private String deliveryTypeStr_en;
    private String deliveryTypeStr_th;

    private Integer deliveryType_2;
    private String deliveryType_2Str_en;
    private String deliveryType_2Str_th;

    private String calculationOrder;
    private String calculationOrderStr_th;
    private String calculationOrderStr_en;

    private String typeOfGood;
    private String typeOfGoodStr_th;
    private String typeOfGoodStr_en;

    private Integer unit;
    private String unitStr_en;
    private String unitStr_th;

    private String branchId;
    private String senderId;
    private String senderPhone;
    private String pickupPoint;
    private Double pickupLatitude;
    private Double pickupLongtitude;
    private String pickupNote;
    private String invoiceDate;
    private String saleOrderNo;
    private String documentNo;
    private String productNo;
    private String referenceNo;
    private String deliveryId;
    private Double deliveryLatitude;
    private Double deliveryLongtitude;
    private String deliveryNote;
    private String receiverPhone;
    
    private String areaMasterId;
    private String cod;
    
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
    
    private String originShipment;
    // private String deliveryShipment;
    // auto imcreament
    private String deliveryOrderNumber;
    private Integer splited;
    private Integer billStatus;
    
    private String otherNumber;
    private String shipTo;
    private Double special;
    private Integer loadingPiece;
    private Double loadingBoxNoSize;
    private Double amountOfMoney;
    private Double specialSize;
    private String driverPhone;
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
    private String deliveryOrderKey;
    private String createdAt;
    private String updatedAt;
//    private IdAreaMasterResponse areaMasterResponse;
//    private List<DeliveryOrderHistoryResponse> history = new ArrayList<>();
//    private List<DocumentResponse> documents = new ArrayList<>();
}

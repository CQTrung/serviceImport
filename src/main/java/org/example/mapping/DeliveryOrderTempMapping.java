package org.example.mapping;


import org.example.DTO.DeliveryOrderImportExcelRequest;
import org.example.entity.DtbRttDeliveryOrderTemp;

public class DeliveryOrderTempMapping {

    

    private static int counter = 0;


    public static DtbRttDeliveryOrderTemp convertToEntity(DeliveryOrderImportExcelRequest deliveryOrderRequest) {
        DtbRttDeliveryOrderTemp deliveryOrder = new DtbRttDeliveryOrderTemp();
        // counter++;
        // String counterString = String.format("%05d", counter);
        // // Get the current year and date
        // // int currentYear = LocalDate.now().getYear();
        // String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // // Concatenate brandID, currentYear, and currentDate
        // String deliveryOrderNumber = deliveryOrderRequest.getBranchId() + currentDate + counterString;
        // deliveryOrder.setDeliveryOrderNumber(deliveryOrderNumber);
        deliveryOrder.setDeliveryOrderNumber(deliveryOrderRequest.getDeliveryOrderNumber());
        deliveryOrder.setDeliveryStatus(deliveryOrderRequest.getDeliveryStatus());
        deliveryOrder.setCompanyId(deliveryOrderRequest.getCompanyId());
        deliveryOrder.setDocumentType(deliveryOrderRequest.getDocumentType());
        deliveryOrder.setDocumentType_2(deliveryOrderRequest.getDocumentType_2());
        deliveryOrder.setCalculationOrder(deliveryOrderRequest.getCalculationOrder());
        deliveryOrder.setShipTo(deliveryOrderRequest.getShipTo());
        deliveryOrder.setDeliveryType(deliveryOrderRequest.getDeliveryType());
        deliveryOrder.setBranchId(deliveryOrderRequest.getBranchId());
        deliveryOrder.setSenderId(deliveryOrderRequest.getSenderId());
        deliveryOrder.setSenderName(deliveryOrderRequest.getSenderName());
        deliveryOrder.setSenderPhone(deliveryOrderRequest.getSenderPhone());
        deliveryOrder.setPickupId(deliveryOrderRequest.getPickupId());
        deliveryOrder.setPickupPoint(deliveryOrderRequest.getPickupPoint());
        deliveryOrder.setPickupTime(deliveryOrderRequest.getPickupTime());
        deliveryOrder.setPickupLatitude(deliveryOrderRequest.getPickupLatitude());
        deliveryOrder.setPickupLongtitude(deliveryOrderRequest.getPickupLongtitude());
        deliveryOrder.setPickupNote(deliveryOrderRequest.getPickupNote());
        deliveryOrder.setTypeOfGood(deliveryOrderRequest.getTypeOfGood());
        deliveryOrder.setInvoiceNo(deliveryOrderRequest.getInvoiceNo());
        deliveryOrder.setInvoiceDate(deliveryOrderRequest.getInvoiceDate());
        deliveryOrder.setSaleOrderNo(deliveryOrderRequest.getSaleOrderNo());
        deliveryOrder.setDocumentNo(deliveryOrderRequest.getDocumentNo());
        deliveryOrder.setProductNo(deliveryOrderRequest.getProductNo());
        deliveryOrder.setReferenceNo(deliveryOrderRequest.getReferenceNo());
        deliveryOrder.setDeliveryId(deliveryOrderRequest.getDeliveryId());
        deliveryOrder.setDeliveryPoint(deliveryOrderRequest.getDeliveryPoint());
        deliveryOrder.setDeliveryLatitude(deliveryOrderRequest.getDeliveryLatitude());
        deliveryOrder.setDeliveryLongtitude(deliveryOrderRequest.getDeliveryLongtitude());
        deliveryOrder.setDeliveryNote(deliveryOrderRequest.getDeliveryNote());
        deliveryOrder.setReceiverName(deliveryOrderRequest.getReceiverName());
        deliveryOrder.setReceiverPhone(deliveryOrderRequest.getReceiverPhone());
        deliveryOrder.setUnit(deliveryOrderRequest.getUnit());
        deliveryOrder.setAreaMasterId(deliveryOrderRequest.getAreaMasterId());
        deliveryOrder.setDueDate(deliveryOrderRequest.getDueDate());
        deliveryOrder.setLoadingBox(deliveryOrderRequest.getLoadingBox());
        deliveryOrder.setLoadingWeight(deliveryOrderRequest.getLoadingWeight());
        deliveryOrder.setLoadingLiter(deliveryOrderRequest.getLoadingLiter());
        deliveryOrder.setLoadingCbm(deliveryOrderRequest.getLoadingCbm());
        deliveryOrder.setLoadingTarget(deliveryOrderRequest.getLoadingTarget());
        deliveryOrder.setSs(deliveryOrderRequest.getSs());
        deliveryOrder.setS(deliveryOrderRequest.getS());
        deliveryOrder.setM(deliveryOrderRequest.getM());
        deliveryOrder.setL(deliveryOrderRequest.getL());
        deliveryOrder.setXl(deliveryOrderRequest.getXl());
        deliveryOrder.setXxl(deliveryOrderRequest.getXxl());
        deliveryOrder.setXxxl(deliveryOrderRequest.getXxxl());
        deliveryOrder.setXxxxl(deliveryOrderRequest.getXxxxl());
        deliveryOrder.setXxxxxl(deliveryOrderRequest.getXxxxxl());
        deliveryOrder.setProductSize(deliveryOrderRequest.getProductSize());
        deliveryOrder.setShipmentId(deliveryOrderRequest.getShipmentId());
        deliveryOrder.setShipmentType(deliveryOrderRequest.getShipmentType());
        deliveryOrder.setShipmentArea(deliveryOrderRequest.getShipmentArea());
        deliveryOrder.setStatusType(deliveryOrderRequest.getStatusType());
        deliveryOrder.setDeliveryStatus(deliveryOrderRequest.getDeliveryStatus());
        deliveryOrder.setOrderDate(deliveryOrderRequest.getOrderDate());
        deliveryOrder.setInvoiceReceivingDate(deliveryOrderRequest.getInvoiceReceivingDate());
        deliveryOrder.setAreaOfResponsibility(deliveryOrderRequest.getAreaOfResponsibility());
        deliveryOrder.setRegion(deliveryOrderRequest.getRegion());
        deliveryOrder.setPallet(deliveryOrderRequest.getPallet());
        deliveryOrder.setInsurance(deliveryOrderRequest.getInsurance());
        deliveryOrder.setDeliveryNote_2(deliveryOrderRequest.getDeliveryNote_2());
        deliveryOrder.setDeliveryNote_3(deliveryOrderRequest.getDeliveryNote_3());
        deliveryOrder.setDeliveryNote_4(deliveryOrderRequest.getDeliveryNote_4());
        String key = deliveryOrderRequest.getCompanyId() + deliveryOrderRequest.getBranchId()+ deliveryOrderRequest.getSenderId() + deliveryOrderRequest.getInvoiceNo() + deliveryOrderRequest.getDeliveryType() + deliveryOrderRequest.getDocumentType();
        deliveryOrder.setDeliveryOrderKey(key);
        // deliveryOrder.setSplited(1);
        return deliveryOrder;
    }
}
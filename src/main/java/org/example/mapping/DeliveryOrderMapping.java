package org.example.mapping;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.example.DTO.DeliveryOrderImportExcelRequest;
import org.example.DTO.DeliveryOrderRequest;
import org.example.entity.DtbRttDeliveryOrder;
import org.example.enums.DeliveryOrderStatus;
import org.springframework.util.StringUtils;



public class DeliveryOrderMapping {



    private static int counter = 0;

    public static DtbRttDeliveryOrder convertFromExcel(DeliveryOrderImportExcelRequest request) {
        DtbRttDeliveryOrder dtbRttDeliveryOrder = new DtbRttDeliveryOrder();

        if(!StringUtils.hasText(request.getDeliveryOrderNumber())){
            counter++;
            String counterString = String.format("%05d", counter);
            // Get the current year and date
            // int currentYear = LocalDate.now().getYear();
            String currentDate = LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));;
            // Concatenate brandID, currentYear, and currentDate
            String deliveryOrderNumber = request.getBranchId() + currentDate + counterString;
            dtbRttDeliveryOrder.setDeliveryOrderNumber(deliveryOrderNumber);
        }else{
            dtbRttDeliveryOrder.setDeliveryOrderNumber(request.getDeliveryOrderNumber());
        }

        dtbRttDeliveryOrder.setCompanyId(request.getCompanyId());
        dtbRttDeliveryOrder.setDocumentType(request.getDocumentType());
        dtbRttDeliveryOrder.setCalculationOrder(request.getCalculationOrder());
        dtbRttDeliveryOrder.setDeliveryType(request.getDeliveryType());
        dtbRttDeliveryOrder.setBranchId(request.getBranchId());
        dtbRttDeliveryOrder.setSenderId(request.getSenderId());
        dtbRttDeliveryOrder.setSenderName(request.getSenderName());
        dtbRttDeliveryOrder.setSenderPhone(request.getSenderPhone());
        dtbRttDeliveryOrder.setPickupId(request.getPickupId());
        dtbRttDeliveryOrder.setPickupPoint(request.getPickupPoint());
        dtbRttDeliveryOrder.setPickupTime(request.getPickupTime());
        dtbRttDeliveryOrder.setPickupLatitude(request.getPickupLatitude());
        dtbRttDeliveryOrder.setPickupLongtitude(request.getPickupLongtitude());
        dtbRttDeliveryOrder.setPickupNote(request.getPickupNote());
        dtbRttDeliveryOrder.setTypeOfGood(request.getTypeOfGood());
        dtbRttDeliveryOrder.setInvoiceNo(request.getInvoiceNo());
        dtbRttDeliveryOrder.setInvoiceDate(request.getInvoiceDate());
        dtbRttDeliveryOrder.setSaleOrderNo(request.getSaleOrderNo());
        dtbRttDeliveryOrder.setDocumentNo(request.getDocumentNo());
        dtbRttDeliveryOrder.setProductNo(request.getProductNo());
        dtbRttDeliveryOrder.setReferenceNo(request.getReferenceNo());
        dtbRttDeliveryOrder.setDeliveryId(request.getDeliveryId());
        dtbRttDeliveryOrder.setDeliveryLatitude(request.getDeliveryLatitude());
        dtbRttDeliveryOrder.setDeliveryLongtitude(request.getDeliveryLongtitude());
        dtbRttDeliveryOrder.setDeliveryNote(request.getDeliveryNote());
        dtbRttDeliveryOrder.setReceiverName(request.getReceiverName());
        dtbRttDeliveryOrder.setReceiverPhone(request.getReceiverPhone());
        dtbRttDeliveryOrder.setUnit(request.getUnit());
        dtbRttDeliveryOrder.setAreaMasterId(request.getAreaMasterId());
        dtbRttDeliveryOrder.setDueDate(request.getDueDate());
        dtbRttDeliveryOrder.setLoadingBox(request.getLoadingBox());
        dtbRttDeliveryOrder.setLoadingWeight(request.getLoadingWeight());
        dtbRttDeliveryOrder.setLoadingLiter(request.getLoadingLiter());
        dtbRttDeliveryOrder.setLoadingCbm(request.getLoadingCbm());
        dtbRttDeliveryOrder.setLoadingTarget(request.getLoadingTarget());
        dtbRttDeliveryOrder.setSs(request.getSs());
        dtbRttDeliveryOrder.setS(request.getS());
        dtbRttDeliveryOrder.setM(request.getM());
        dtbRttDeliveryOrder.setL(request.getL());
        dtbRttDeliveryOrder.setXl(request.getXl());
        dtbRttDeliveryOrder.setXxl(request.getXxl());
        dtbRttDeliveryOrder.setXxxl(request.getXxxl());
        dtbRttDeliveryOrder.setXxxxl(request.getXxxxl());
        dtbRttDeliveryOrder.setXxxxxl(request.getXxxxxl());
        dtbRttDeliveryOrder.setProductSize(request.getProductSize());
        dtbRttDeliveryOrder.setShipmentId(request.getShipmentId());
        dtbRttDeliveryOrder.setShipmentType(request.getShipmentType());
        dtbRttDeliveryOrder.setShipmentArea(request.getShipmentArea());
        dtbRttDeliveryOrder.setDeliveryType_2(request.getDeliveryType_2());
        dtbRttDeliveryOrder.setDocumentType_2(request.getDocumentType_2());
        dtbRttDeliveryOrder.setShipTo(request.getShipTo());
        dtbRttDeliveryOrder.setOtherNumber(request.getOtherNumber());
        dtbRttDeliveryOrder.setSpecial(request.getSpecial());
        dtbRttDeliveryOrder.setLoadingBoxNoSize(request.getLoadingBoxNoSize());
        dtbRttDeliveryOrder.setLoadingPiece(request.getLoadingPiece());
        dtbRttDeliveryOrder.setAmountOfMoney(request.getAmountOfMoney());
        dtbRttDeliveryOrder.setSs5xl(request.getSs5xl());
        dtbRttDeliveryOrder.setSpecialSize(request.getSpecialSize());
        dtbRttDeliveryOrder.setDriverPhone(request.getDriverPhone());
        dtbRttDeliveryOrder.setStatus(DeliveryOrderStatus.TO_DO.getValue());
        dtbRttDeliveryOrder.setStatusType(request.getStatusType());
        dtbRttDeliveryOrder.setDeliveryStatus(request.getDeliveryStatus());
        dtbRttDeliveryOrder.setOrderDate(request.getOrderDate());
        dtbRttDeliveryOrder.setDeliveryPoint(request.getDeliveryPoint());
        dtbRttDeliveryOrder.setInvoiceReceivingDate(request.getInvoiceReceivingDate());
        dtbRttDeliveryOrder.setAreaOfResponsibility(request.getAreaOfResponsibility());
        dtbRttDeliveryOrder.setRegion(request.getRegion());
        dtbRttDeliveryOrder.setPallet(request.getPallet());
        dtbRttDeliveryOrder.setInsurance(request.getInsurance());
        dtbRttDeliveryOrder.setDeliveryNote_2(request.getDeliveryNote_2());
        dtbRttDeliveryOrder.setDeliveryNote_3(request.getDeliveryNote_3());
        dtbRttDeliveryOrder.setDeliveryNote_4(request.getDeliveryNote_4());
        dtbRttDeliveryOrder.setOriginShipment(request.getOriginShipment());
        String key = request.getCompanyId() + request.getBranchId()+ request.getSenderId() + request.getInvoiceNo() + request.getDocumentType() + request.getDocumentType_2();
        dtbRttDeliveryOrder.setDeliveryOrderKey(key);
        return dtbRttDeliveryOrder;
    }

    public static DtbRttDeliveryOrder convertToEntity(DeliveryOrderRequest deliveryOrderRequest) {
        DtbRttDeliveryOrder deliveryOrder = new DtbRttDeliveryOrder();
        counter++;
        String counterString = String.format("%05d", counter);
        // Get the current year and date
        // int currentYear = LocalDate.now().getYear();
        String currentDate = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"));
        // Concatenate brandID, currentYear, and currentDate
        String deliveryOrderNumber = deliveryOrderRequest.getBranchId() + currentDate + counterString;
        deliveryOrder.setDeliveryOrderNumber(deliveryOrderNumber);
        deliveryOrder.setDeliveryStatus(deliveryOrderRequest.getDeliveryStatus());
        deliveryOrder.setCompanyId(deliveryOrderRequest.getCompanyId());
        deliveryOrder.setDocumentType(deliveryOrderRequest.getDocumentType());
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
        deliveryOrder.setStatus(deliveryOrderRequest.getStatus());
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
        deliveryOrder.setProvince(deliveryOrderRequest.getProvince());
        deliveryOrder.setDistrict(deliveryOrderRequest.getDistrict());
        deliveryOrder.setSubDistrict(deliveryOrderRequest.getSubDistrict());
        String key = deliveryOrderRequest.getCompanyId() + deliveryOrderRequest.getBranchId()+ deliveryOrderRequest.getSenderId() + deliveryOrderRequest.getInvoiceNo() + deliveryOrderRequest.getDeliveryType() + deliveryOrderRequest.getDocumentType();
        deliveryOrder.setDeliveryOrderKey(key);
        // deliveryOrder.setSplited(1);
        return deliveryOrder;
    }

    public static DtbRttDeliveryOrder convertToUpdate(DtbRttDeliveryOrder deliveryOrder, DeliveryOrderImportExcelRequest deliveryOrderRequest) {
        deliveryOrder.setDeliveryOrderNumber(deliveryOrder.getDeliveryOrderNumber());
        if (deliveryOrderRequest.getStatusType().length()<3) {
            deliveryOrder.setDeliveryStatus("D");
        }else if (deliveryOrderRequest.getStatusType().length()>3) {
            deliveryOrder.setDeliveryStatus("T");
        }
        deliveryOrder.setDeliveryStatus(deliveryOrderRequest.getDeliveryStatus());
        deliveryOrder.setDocumentType(deliveryOrder.getDocumentType());
        deliveryOrder.setCalculationOrder(deliveryOrderRequest.getCalculationOrder());
        deliveryOrder.setDeliveryType(deliveryOrderRequest.getDeliveryType());
        deliveryOrder.setSenderName(deliveryOrderRequest.getSenderName());
        deliveryOrder.setSenderPhone(deliveryOrderRequest.getSenderPhone());
        deliveryOrder.setPickupId(deliveryOrderRequest.getPickupId());
        deliveryOrder.setPickupPoint(deliveryOrderRequest.getPickupPoint());
        deliveryOrder.setPickupTime(deliveryOrderRequest.getPickupTime());
        deliveryOrder.setPickupLatitude(deliveryOrderRequest.getPickupLatitude());
        deliveryOrder.setPickupLongtitude(deliveryOrderRequest.getPickupLongtitude());
        deliveryOrder.setPickupNote(deliveryOrderRequest.getPickupNote());
        deliveryOrder.setTypeOfGood(deliveryOrderRequest.getTypeOfGood());
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
        deliveryOrder.setDeliveryType_2(deliveryOrderRequest.getDeliveryType_2());
        deliveryOrder.setDocumentType_2(deliveryOrder.getDocumentType_2());
        deliveryOrder.setShipTo(deliveryOrderRequest.getShipTo());
        deliveryOrder.setOtherNumber(deliveryOrderRequest.getOtherNumber());
        deliveryOrder.setSpecial(deliveryOrderRequest.getSpecial());
        deliveryOrder.setSpecialSize(deliveryOrderRequest.getSpecialSize());
        deliveryOrder.setDriverPhone(deliveryOrderRequest.getDriverPhone());
        deliveryOrder.setLoadingBoxNoSize(deliveryOrderRequest.getLoadingBoxNoSize());
        deliveryOrder.setLoadingPiece(deliveryOrderRequest.getLoadingPiece());
        deliveryOrder.setAmountOfMoney(deliveryOrderRequest.getAmountOfMoney());
        deliveryOrder.setSs5xl(deliveryOrderRequest.getSs5xl());
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
        deliveryOrder.setOriginShipment(deliveryOrderRequest.getOriginShipment());
        // deliveryOrder.setDeliveryOrderKey(deliveryOrderRequest.getDeliveryOrderKey());
        deliveryOrder.setDeliveryOrderKey(deliveryOrder.getDeliveryOrderKey());
        return deliveryOrder;
    }
}

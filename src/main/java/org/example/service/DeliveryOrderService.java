package org.example.service;

import org.example.DTO.DeliveryOrderImportExcelResponse;
import org.example.DTO.ShipmentOrderImportExcelRequest;
import org.example.DTO.ShipmentOrderImportExcelResponse;
import org.example.entity.DtbRttDeliveryOrder;
import org.example.entity.DtbRttDeliveryOrderTemp;
import org.example.enums.DeliveryOrderStatus;
import org.example.exception.AppValidationException;
import org.example.helper.DateTimeHelper;
import org.example.mapping.DeliveryOrderMapping;
import org.example.mapping.DeliveryOrderTempMapping;
import org.example.repository.DeliveryOrderRepository;
import org.example.repository.DeliveryOrderTempRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.midi.Soundbank;
import java.io.IOException;
import java.util.*;

@Service
public class DeliveryOrderService extends BaseService {

    protected final ImportExcelService importExcelService;

    protected final DeliveryOrderRepository deliveryOrderRepository;
    protected final DeliveryOrderTempRepository deliveryOrderTempRepository;

    public DeliveryOrderService(ImportExcelService importExcelService,
                                DeliveryOrderRepository deliveryOrderRepository,
                                DeliveryOrderTempRepository deliveryOrderTempRepository) {
        this.importExcelService = importExcelService;
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.deliveryOrderTempRepository = deliveryOrderTempRepository;
    }

    int counter;

    public ResponseEntity<?> importNewDeliveryOrderRtt(MultipartFile file)
            throws IOException {
        if (file != null) {
            String importKeys = DateTimeHelper.getSaltString();
            String fileType = file.getContentType() != null ? file.getContentType().toLowerCase() : "";
            if (!(fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))) {
                throw new AppValidationException("invalid Excel File");
            }
            if (file.getBytes().length > 999999999) {
                throw new AppValidationException("invalid Size File");
            }

            HashMap<Integer, ArrayList<Object>> dataExcel = importExcelService.importFileExcelBySheet(file, 0);
            List<ShipmentOrderImportExcelRequest> shipmentOrderList = ShipmentOrderImportExcelRequest
                    .convertExcelToNewDeliveryOrder2(dataExcel);

            DeliveryOrderImportExcelResponse responseInport = new DeliveryOrderImportExcelResponse();

            List<ShipmentOrderImportExcelResponse> DOFails = new ArrayList<>();

            ShipmentOrderImportExcelResponse DOError = new ShipmentOrderImportExcelResponse();
            DOError.setImportKey(importKeys);
            List<ShipmentOrderImportExcelResponse> DOValid = new ArrayList<>();
            List<ShipmentOrderImportExcelResponse> DOUpdate = new ArrayList<>();

            int[] status = {11, 17};
            for (ShipmentOrderImportExcelRequest shipmentOrder : shipmentOrderList) {
                // List<Object> count = new ArrayList<>();
                DtbRttDeliveryOrder dtbRttDeliveryOrder = DeliveryOrderMapping
                        .convertFromExcel(shipmentOrder.getDeliverOrderRequest());

                // if (
                // StringUtils.hasText(shipmentOrder.getDeliverOrderRequest().getDeliveryOrderNumber()))
                // {
                // Optional<DtbRttDeliveryOrder> check =
                // deliveryOrderRepository.findByDeliveryOrderNumberAndIsDeletedFalse(shipmentOrder.getDeliverOrderRequest().getDeliveryOrderNumber());
                // count =
                // deliveryOrderRepository.countDeliveryOrderByKey(check.get().getDeliveryOrderKey());
                // }

                List<Object> count = deliveryOrderRepository
                        .countDeliveryOrderByKey(shipmentOrder.getDeliverOrderRequest().getDeliveryOrderKey());
                if (StringUtils.hasText(shipmentOrder.getDeliverOrderRequest().getDeliveryOrderNumber())) {
                    Optional<DtbRttDeliveryOrder> updateDo = deliveryOrderRepository.checkDeliveryOrderUpdateimport(
                            shipmentOrder.getDeliverOrderRequest().getDeliveryOrderNumber());
                    if (updateDo.isPresent()) {
                        DtbRttDeliveryOrder doUpdate = DeliveryOrderMapping.convertToUpdate(updateDo.get(),
                                shipmentOrder.getDeliverOrderRequest());
                        if (shipmentOrder.getDeliverOrderRequest().getStatusType().length() < 3) {
                            doUpdate.setDeliveryStatus("D");
                        } else if (shipmentOrder.getDeliverOrderRequest().getStatusType().length() > 3) {
                            doUpdate.setDeliveryStatus("T");
                        }
                        ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                        shipmentOrderResponse.setImportKey(null);
                        shipmentOrderResponse.setErrorDetail(null);
                        shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                        DOUpdate.add(shipmentOrderResponse);
                        deliveryOrderRepository.save(doUpdate);
                        System.out.println("co update !!!");
                        continue;
                    } else {
                        // shipmentOrder.setErrorDetail("Delivery Order Number not found");
                        DtbRttDeliveryOrderTemp deliveryOrderTemp = DeliveryOrderTempMapping
                                .convertToEntity(shipmentOrder.getDeliverOrderRequest());
                        deliveryOrderTemp.setImportKey(importKeys);
                        deliveryOrderTemp.setReasonErr("Delivery Order Number not found");
                        deliveryOrderTempRepository.save(deliveryOrderTemp);

                        // DOError.setErrorDetail("Delivery Order Number not found");
                        ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                        shipmentOrderResponse.setImportKey(importKeys);
                        shipmentOrderResponse.setErrorDetail("Delivery Order Number not found");
                        shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                        DOFails.add(shipmentOrderResponse);
                        // continue;
                        // listDO.add(shipmentOrder.getDeliverOrderRequest());
                    }
                }
                if (count.size() > 0) {

                    for (Object obj : count) {
                        int statusValue = (Integer) obj;
                        if (Arrays.stream(status).anyMatch(x -> x == statusValue)) {
                            dtbRttDeliveryOrder.setStatus(DeliveryOrderStatus.UNASSIGNED.getValue());
                            if (StringUtils.hasText(dtbRttDeliveryOrder.getBranchId())
                                    && !dtbRttDeliveryOrder.getBranchId().contains("null")) {
                                if (dtbRttDeliveryOrder.getStatusType().length() < 2) {
                                    dtbRttDeliveryOrder.setDeliveryStatus("D");
                                } else {
                                    dtbRttDeliveryOrder.setDeliveryStatus("T");
                                }
                                deliveryOrderRepository.save(dtbRttDeliveryOrder);
                                System.out.println("co luu");
                            }
                        } else {

                            DtbRttDeliveryOrderTemp deliveryOrderTemp = DeliveryOrderTempMapping
                                    .convertToEntity(shipmentOrder.getDeliverOrderRequest());
                            deliveryOrderTemp.setImportKey(importKeys);
                            deliveryOrderTemp.setReasonErr("key duplicated");
                            deliveryOrderTempRepository.save(deliveryOrderTemp);
                            ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                            shipmentOrderResponse.setImportKey(importKeys);
                            shipmentOrderResponse.setErrorDetail("Key duplicated");
                            shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                            DOFails.add(shipmentOrderResponse);
                        }
                    }
                    continue;
                }

                if (!StringUtils.hasText(shipmentOrder.getDeliverOrderRequest().getDeliveryOrderNumber())) {
                    String key = shipmentOrder.getDeliverOrderRequest().getCompanyId()
                            + shipmentOrder.getDeliverOrderRequest().getBranchId() +
                            shipmentOrder.getDeliverOrderRequest().getSenderId()
                            + shipmentOrder.getDeliverOrderRequest().getInvoiceNo() +
                            shipmentOrder.getDeliverOrderRequest().getDocumentType()
                            + shipmentOrder.getDeliverOrderRequest().getDocumentType_2();

                    List<Object> checkKeyInsert = deliveryOrderRepository.countDeliveryOrderByKey(key);

                    List<String> checkKey = deliveryOrderRepository
                            .countInvoiceAndIsDeletedFalse(dtbRttDeliveryOrder.getInvoiceNo());

                    if (checkKeyInsert.isEmpty()) {

                        if (StringUtils.hasText(dtbRttDeliveryOrder.getBranchId())
                                && !dtbRttDeliveryOrder.getBranchId().contains("null")) {
                            dtbRttDeliveryOrder.setStatus(DeliveryOrderStatus.UNASSIGNED.getValue());
                            if (dtbRttDeliveryOrder.getStatusType().length() < 2) {
                                dtbRttDeliveryOrder.setDeliveryStatus("D");
                            } else {
                                dtbRttDeliveryOrder.setDeliveryStatus("T");
                            }
                            if (!checkKey.isEmpty()) {
                                String concatenatedString = String.join(",", checkKey);
                                dtbRttDeliveryOrder.setOriginSite(concatenatedString);
                            }

                            ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                            shipmentOrderResponse.setImportKey(null);
                            shipmentOrderResponse.setErrorDetail(null);
                            shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                            DOValid.add(shipmentOrderResponse);
                            deliveryOrderRepository.save(dtbRttDeliveryOrder);
                            System.out.println("cos lưu !!!!");
                            continue;
                        } else {
                            // shipmentOrder.setErrorDetail("Site ID Null");
                            DtbRttDeliveryOrderTemp deliveryOrderTemp = DeliveryOrderTempMapping
                                    .convertToEntity(shipmentOrder.getDeliverOrderRequest());
                            deliveryOrderTemp.setImportKey(importKeys);
                            deliveryOrderTemp.setReasonErr("Site ID Null");
                            deliveryOrderTempRepository.save(deliveryOrderTemp);

                            // DOError.setErrorDetail("Delivery Order Number not found");
                            ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                            shipmentOrderResponse.setImportKey(importKeys);
                            shipmentOrderResponse.setErrorDetail("Site ID Null");
                            shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                            DOFails.add(shipmentOrderResponse);
                            // listDO.add(shipmentOrder.getDeliverOrderRequest());
                        }

                    } else {
                        DtbRttDeliveryOrderTemp deliveryOrderTemp = DeliveryOrderTempMapping
                                .convertToEntity(shipmentOrder.getDeliverOrderRequest());
                        deliveryOrderTemp.setImportKey(importKeys);
                        deliveryOrderTemp.setReasonErr("key duplicated");
                        deliveryOrderTempRepository.save(deliveryOrderTemp);
                        ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                        shipmentOrderResponse.setImportKey(importKeys);
                        shipmentOrderResponse.setErrorDetail("Key duplicated");
                        shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                        DOFails.add(shipmentOrderResponse);
                    }
                }

            }

            // if (!DOFails.isEmpty()) {
            // return ResponseEntity.ok().body(DOFails);
            // }

            responseInport.setTotal(shipmentOrderList.size());
            responseInport.setImportfail(DOFails);
            responseInport.setInsertRecord(DOValid);
            responseInport.setUpdateRecord(DOUpdate);

            if(DOValid.size()>0){
                System.out.println("co du lieu");
            }
            if (DOUpdate.size()>0){
                System.out.println("update du lieu");
            }
            if (DOFails.size()>0){
                System.out.println("du lieu loi");
            }

            return ResponseEntity.ok().body(responseInport);
        }
        return ResponseEntity.badRequest().build();
    }
}
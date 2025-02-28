package org.example.service;

import org.example.DTO.DeliveryOrderImportExcelResponse;
import org.example.DTO.ShipmentOrderImportExcelRequest;
import org.example.DTO.ShipmentOrderImportExcelResponse;
import org.example.entity.DtbRttDeliveryOrder;
import org.example.entity.DtbRttDeliveryOrderFile;
import org.example.entity.DtbRttDeliveryOrderTemp;
import org.example.enums.DeliveryOrderStatus;
import org.example.exception.AppValidationException;
import org.example.helper.DateTimeHelper;
import org.example.mapping.DeliveryOrderMapping;
import org.example.mapping.DeliveryOrderTempMapping;
import org.example.repository.DeliveryOrderFileRepository;
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

    protected  final DeliveryOrderFileRepository deliveryOrderFileRepository;

    public DeliveryOrderService(ImportExcelService importExcelService,
                                DeliveryOrderRepository deliveryOrderRepository,
                                DeliveryOrderTempRepository deliveryOrderTempRepository,
                                DeliveryOrderFileRepository deliveryOrderFileRepository) {
        this.importExcelService = importExcelService;
        this.deliveryOrderRepository = deliveryOrderRepository;
        this.deliveryOrderTempRepository = deliveryOrderTempRepository;
        this.deliveryOrderFileRepository = deliveryOrderFileRepository;
    }

    int counter;

    @Transactional
    public ResponseEntity<?> importNewDeliveryOrderRtt(MultipartFile file, DtbRttDeliveryOrderFile deliveryOrderFilefile) throws IOException {
        if (file == null || file.getBytes().length > 999999999) {
            return ResponseEntity.badRequest().body("Invalid File or File Size");
        }

        String importKeys = DateTimeHelper.getSaltString();
        String fileType = Optional.ofNullable(file.getContentType()).orElse("").toLowerCase();

        if (!fileType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
            throw new AppValidationException("Invalid Excel File");
        }

        HashMap<Integer, ArrayList<Object>> dataExcel = importExcelService.importFileExcelBySheet(file, 0);
        List<ShipmentOrderImportExcelRequest> shipmentOrderList =
                ShipmentOrderImportExcelRequest.convertExcelToNewDeliveryOrder2(dataExcel);

        DeliveryOrderImportExcelResponse responseInport = new DeliveryOrderImportExcelResponse();
        List<ShipmentOrderImportExcelResponse> DOFails = new ArrayList<>();
        List<ShipmentOrderImportExcelResponse> DOValid = new ArrayList<>();
        List<ShipmentOrderImportExcelResponse> DOUpdate = new ArrayList<>();

        List<DtbRttDeliveryOrder> validOrders = new ArrayList<>();
        List<DtbRttDeliveryOrder> updateOrders = new ArrayList<>();
        List<DtbRttDeliveryOrderTemp> tempOrders = new ArrayList<>();

        int[] status = {11, 17};
        int batchSize = 500;
        int totalSize = shipmentOrderList.size();
        int processedOrders = 0;
        int progress = 0;
        int lastProgress = 0;

        for (ShipmentOrderImportExcelRequest shipmentOrder : shipmentOrderList) {
            DtbRttDeliveryOrder dtbRttDeliveryOrder =
                    DeliveryOrderMapping.convertFromExcel(shipmentOrder.getDeliverOrderRequest());

            List<Object> count = deliveryOrderRepository
                    .countDeliveryOrderByKey(shipmentOrder.getDeliverOrderRequest().getDeliveryOrderKey());

            String deliveryOrderNumber = shipmentOrder.getDeliverOrderRequest().getDeliveryOrderNumber();

            // 🟢 Kiểm tra cập nhật nếu đã có DeliveryOrderNumber
            if (StringUtils.hasText(deliveryOrderNumber)) {
                Optional<DtbRttDeliveryOrder> updateDo =
                        deliveryOrderRepository.checkDeliveryOrderUpdateimport(deliveryOrderNumber);

                if (updateDo.isPresent()) {
                    DtbRttDeliveryOrder doUpdate = DeliveryOrderMapping.convertToUpdate(updateDo.get(),
                            shipmentOrder.getDeliverOrderRequest());
                    doUpdate.setDeliveryStatus(doUpdate.getStatusType().length() < 3 ? "D" : "T");

                    ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                        shipmentOrderResponse.setImportKey(null);
                        shipmentOrderResponse.setErrorDetail(null);
                        shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                        DOUpdate.add(shipmentOrderResponse);
                    updateOrders.add(doUpdate);

                    if (updateOrders.size() >= batchSize) {
                        deliveryOrderRepository.saveAll(updateOrders);
                        updateOrders.clear();
                    }
                    continue;
                }else {
                    DtbRttDeliveryOrderTemp deliveryOrderTemp =
                            DeliveryOrderTempMapping.convertToEntity(shipmentOrder.getDeliverOrderRequest());
                    deliveryOrderTemp.setImportKey(importKeys);
                    deliveryOrderTemp.setReasonErr("Delivery order not found !!!");

                    tempOrders.add(deliveryOrderTemp);

                    if (tempOrders.size() >= batchSize) {
                        deliveryOrderTempRepository.saveAll(tempOrders);
                        tempOrders.clear();
                    }
                    // Thêm vào error key
                    deliveryOrderFilefile.setErrorKey(deliveryOrderTemp.getImportKey());
                    deliveryOrderFileRepository.save(deliveryOrderFilefile);

                    ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                    shipmentOrderResponse.setImportKey(importKeys);
                    shipmentOrderResponse.setErrorDetail("Key duplicated");
                    shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                    DOFails.add(shipmentOrderResponse);
                }
            }

            // 🟢 Kiểm tra trùng lặp key và xử lý status
            if (!count.isEmpty()) {
                for (Object obj : count) {
                    int statusValue = (Integer) obj;
                    if (Arrays.stream(status).anyMatch(x -> x == statusValue)) {
                        dtbRttDeliveryOrder.setStatus(DeliveryOrderStatus.UNASSIGNED.getValue());
                        dtbRttDeliveryOrder.setDeliveryStatus(
                                dtbRttDeliveryOrder.getStatusType().length() < 2 ? "D" : "T");
                        validOrders.add(dtbRttDeliveryOrder);

                        if (validOrders.size() >= batchSize) {
                            deliveryOrderRepository.saveAll(validOrders);
                            validOrders.clear();
                        }
                        ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                        shipmentOrderResponse.setImportKey(null);
                        shipmentOrderResponse.setErrorDetail(null);
                        shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                        DOValid.add(shipmentOrderResponse);
                    } else {
                        DtbRttDeliveryOrderTemp deliveryOrderTemp =
                                DeliveryOrderTempMapping.convertToEntity(shipmentOrder.getDeliverOrderRequest());
                        deliveryOrderTemp.setImportKey(importKeys);
                        deliveryOrderTemp.setReasonErr("Key duplicated");
                        tempOrders.add(deliveryOrderTemp);

                        if (tempOrders.size() >= batchSize) {
                            deliveryOrderTempRepository.saveAll(tempOrders);
                            tempOrders.clear();
                        }
                        // Thêm vào error key
                        deliveryOrderFilefile.setErrorKey(deliveryOrderTemp.getImportKey());
                        deliveryOrderFileRepository.save(deliveryOrderFilefile);
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
                        validOrders.add(dtbRttDeliveryOrder);

                        if (validOrders.size() >= batchSize) {
                            deliveryOrderRepository.saveAll(validOrders);
                            validOrders.clear();
                        }
                        continue;
                    } else {
                        // shipmentOrder.setErrorDetail("Site ID Null");
                        DtbRttDeliveryOrderTemp deliveryOrderTemp = DeliveryOrderTempMapping
                                .convertToEntity(shipmentOrder.getDeliverOrderRequest());
                        deliveryOrderTemp.setImportKey(importKeys);
                        deliveryOrderTemp.setReasonErr("Site ID Null");
                        tempOrders.add(deliveryOrderTemp);

                        if (tempOrders.size() >= batchSize) {
                            deliveryOrderTempRepository.saveAll(tempOrders);
                            tempOrders.clear();
                        }
                        // Thêm vào error key
                        deliveryOrderFilefile.setErrorKey(deliveryOrderTemp.getImportKey());
                        deliveryOrderFileRepository.save(deliveryOrderFilefile);

                        // DOError.setErrorDetail("Delivery Order Number not found");
                        ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                        shipmentOrderResponse.setImportKey(importKeys);
                        shipmentOrderResponse.setErrorDetail("Site ID Null");
                        shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                        DOFails.add(shipmentOrderResponse);

                        tempOrders.add(deliveryOrderTemp);

                        if (tempOrders.size() >= batchSize) {
                            deliveryOrderTempRepository.saveAll(tempOrders);
                            tempOrders.clear();
                        }
                        // Thêm vào error key
                        deliveryOrderFilefile.setErrorKey(deliveryOrderTemp.getImportKey());
                        deliveryOrderFileRepository.save(deliveryOrderFilefile);
                    }

                } else {
                    DtbRttDeliveryOrderTemp deliveryOrderTemp = DeliveryOrderTempMapping
                            .convertToEntity(shipmentOrder.getDeliverOrderRequest());
                    deliveryOrderTemp.setImportKey(importKeys);
                    deliveryOrderTemp.setReasonErr("key duplicated");
                    tempOrders.add(deliveryOrderTemp);

                    if (tempOrders.size() >= batchSize) {
                        deliveryOrderTempRepository.saveAll(tempOrders);
                        tempOrders.clear();
                    }
                    // Thêm vào error key
                    deliveryOrderFilefile.setErrorKey(deliveryOrderTemp.getImportKey());
                    deliveryOrderFileRepository.save(deliveryOrderFilefile);

                    ShipmentOrderImportExcelResponse shipmentOrderResponse = new ShipmentOrderImportExcelResponse();
                    shipmentOrderResponse.setImportKey(importKeys);
                    shipmentOrderResponse.setErrorDetail("Key duplicated");
                    shipmentOrderResponse.setDeliveryOrder(shipmentOrder.getDeliverOrderRequest());
                    DOFails.add(shipmentOrderResponse);

                }
            }

            processedOrders++;
            progress = Math.min((processedOrders * 100) / totalSize, 100);

            if (processedOrders % 1000 == 0 || processedOrders == totalSize) {
                System.out.println("progress -> :: "+ progress);
                deliveryOrderFilefile.setProgress(progress);
                deliveryOrderFileRepository.save(deliveryOrderFilefile);
            }
        }

        // 🟢 Lưu nốt các bản ghi còn lại nếu chưa đủ batchSize
        if (!validOrders.isEmpty()) {
            deliveryOrderRepository.saveAll(validOrders);
        }

        if (!updateOrders.isEmpty()) {

            deliveryOrderRepository.saveAll(updateOrders);
        }

        if (!tempOrders.isEmpty()) {

            deliveryOrderTempRepository.saveAll(tempOrders);
        }

//        deliveryOrderFilefile.setProgress(100);

        deliveryOrderFilefile.setTotalRecord(shipmentOrderList.size());
        deliveryOrderFilefile.setFailRecord(DOFails.size());
        deliveryOrderFilefile.setSuccessRecord(DOValid.size());
        deliveryOrderFilefile.setUpdateRecord(DOUpdate.size());
        deliveryOrderFileRepository.save(deliveryOrderFilefile);

//        responseInport.setTotal(shipmentOrderList.size());
//        responseInport.setImportfail(DOFails);
//        responseInport.setInsertRecord(DOValid);
//        responseInport.setUpdateRecord(DOUpdate);

        return ResponseEntity.ok().body(responseInport);
    }

}
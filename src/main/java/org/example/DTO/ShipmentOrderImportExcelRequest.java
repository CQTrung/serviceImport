package org.example.DTO;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.example.exception.AppValidationException;
import org.springframework.util.StringUtils;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentOrderImportExcelRequest {
    DeliveryOrderImportExcelRequest deliverOrderRequest;

    public static List<ShipmentOrderImportExcelRequest> convertExcelToNewDeliveryOrder(
            HashMap<Integer, ArrayList<Object>> dataExcel) {
        List<ShipmentOrderImportExcelRequest> list = new ArrayList<>();
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "Delivery Order Number");
        map.put(2, "ID:Company");
        map.put(3, "ID:Site");
        map.put(4, "ID:Sender");
        map.put(5, "Sender's name");
        map.put(6, "Status type");
        map.put(7, "Delivery Status");
        map.put(8, "ID:Pickup");
        map.put(9, "Pickup latitude");
        map.put(10, "Pickup longitude");
        map.put(11, "Pickup Date");
        map.put(12, "Pickup note");
        map.put(13, "Document type1");
        map.put(14, "Document type2");
        map.put(15, "delivery type 1");
        map.put(16, "delivery type 2");
        map.put(17, "Calculation order");
        map.put(18, "Order Date");
        map.put(19, "Invoice Date");
        map.put(20, "Invoice receiving date");
        map.put(21, "Invoice_numbers");
        map.put(22, "sale order_numbers");
        map.put(23, "Document_numbers");
        map.put(24, "Reference_numbers");
        map.put(25, "other_numbers");
        map.put(26, "Ship-to");
        map.put(27, "Receiver's name");
        map.put(28, "Receiver's phone");
        map.put(29, "Delivery latitude");
        map.put(30, "Delivery longitude");
        map.put(31, "Delivery point");
        map.put(32, "Region");
        map.put(33, "Area of responsibility");
        map.put(34, "ID Area Master");
        map.put(35, "Duedate");
        map.put(36, "Product_no");
        map.put(37, "SS");
        map.put(38, "S");
        map.put(39, "M");
        map.put(40, "L");
        map.put(41, "XL");
        map.put(42, "2xL");
        map.put(43, "3xL");
        map.put(44, "4XL");
        map.put(45, "5xL");
        map.put(46, "Special  (Size)");
        map.put(47, "Loading box");
        map.put(48, "Loadding piece/pieces");
        map.put(49, "Unit");
        map.put(50, "Type of good");
        map.put(51, "Loading weight");
        map.put(52, "Loading liter");
        map.put(53, "Loading CBM");
        map.put(54, "Loading target %");
        map.put(55, "amount of money");
        map.put(56, "Special");
        map.put(57, "COD");
        map.put(58, "Pallet");
        map.put(59, "insurance");
        map.put(60, "Delivery Note 1");
        map.put(61, "Delivery Note 2");
        map.put(62, "Delivery Note 3");
        map.put(63, "Delivery Note 4");
        map.put(64, "Origin Shipment");

        Integer count=0;
        for (int index = 2; index <= dataExcel.size(); index++) {
            ArrayList<Object> arrayList = dataExcel.get(index);
            DeliveryOrderImportExcelRequest deliveryOrderImportExcelRequest = new DeliveryOrderImportExcelRequest();
            ShipmentOrderImportExcelRequest shipmentOrderImportExcelRequest = new ShipmentOrderImportExcelRequest();
            if (!arrayList.isEmpty()) {
                for (int i = 0; i < arrayList.size(); i++) {
                    count++;
                    if (!StringUtils.hasText(arrayList.get(20).toString()) && !StringUtils.hasText(arrayList.get(2).toString())) {
                        continue;
                    }
                    try {
                        switch (i) {
                            case 0:
                                if (StringUtils.hasText(arrayList.get(i).toString())) {
                                    deliveryOrderImportExcelRequest.setDeliveryOrderNumber(String.valueOf(arrayList.get(i)));
                                }
                                break;
                            case 1:
                                deliveryOrderImportExcelRequest.setCompanyId(String.valueOf(arrayList.get(i)));
                                break;
                            case 2:
                                // Check if the Site ID is null or empty
                                if (!StringUtils.hasText(arrayList.get(i).toString())) {
                                    // If it's null or empty, you can handle it here
                                    // For example, you might skip this record or log a warning
                                    // You can decide based on your requirements
                                    // Here, we're skipping this record
                                    continue; // Skip processing this record and move to the next one
                                }
                                deliveryOrderImportExcelRequest.setBranchId(String.valueOf(arrayList.get(i)));
                                break;
                            case 3:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String senderIdString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            senderIdString = String.format("%.0f", arrayList.get(i));
                                            if (senderIdString.length()<11) {
                                                senderIdString = "0" + senderIdString;
                                            }
                                        } else {
                                            // If the value is not a double, directly set it
                                            senderIdString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setSenderId(senderIdString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for Sender ID at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i));
                                }
                                break;
                            case 4:
                                deliveryOrderImportExcelRequest.setSenderName(String.valueOf(arrayList.get(i)));
                                break;
                            //statustype
                            case 5:
                                deliveryOrderImportExcelRequest.setStatusType(String.valueOf(arrayList.get(i)));
                                break;
                            case 6:
                                String statusType = String.valueOf(arrayList.get(i-1));
                                if (statusType.equals("D")) {
                                    deliveryOrderImportExcelRequest.setDeliveryStatus("D");
                                }else{
                                    deliveryOrderImportExcelRequest.setDeliveryStatus("T");
                                }
                                break;
                            case 7:
                                deliveryOrderImportExcelRequest.setPickupId(String.valueOf(arrayList.get(i)));
                                break;
                            case 8:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setPickupLatitude(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 9:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {

                                    deliveryOrderImportExcelRequest
                                            .setPickupLongtitude(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 10:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime pickupDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            // DateTimeFormatter formatter = DateTimeFormatter
                                            //         .ofPattern("yyyy-MM-dd hh:mm:ss");
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            System.out.println( "sdksdsds"+formatter);
                                            String formattedPickupTime = pickupDateTime.format(formatter);
                                            System.out.println("sdfsfdfd" + formattedPickupTime);
                                            deliveryOrderImportExcelRequest.setPickupTime(formattedPickupTime);
                                            // Now you can use pickupDateTime as needed
                                        } else {
                                            System.out.println("Invalid value: " + arrayList.get(i).toString());
                                            deliveryOrderImportExcelRequest.setPickupTime(arrayList.get(i).toString());

                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Pickup Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 11:
                                deliveryOrderImportExcelRequest.setPickupNote(String.valueOf(arrayList.get(i)));
                                break;
                            case 12:

                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setDocumentType((int) Double.parseDouble(arrayList.get(i).toString()));
                                }
                                break;
                            case 13:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {

                                    deliveryOrderImportExcelRequest
                                            .setDocumentType_2((int) Double.parseDouble(arrayList.get(i).toString()));
                                }
                                break;
                            case 14:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setDeliveryType((int) Double.parseDouble(arrayList.get(i).toString()));
                                }
                                break;
                            case 15:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setDeliveryType_2((int) Double.parseDouble(arrayList.get(i).toString()));
                                }
                                break;
                            case 16:
                                deliveryOrderImportExcelRequest.setCalculationOrder(String.valueOf(arrayList.get(i)));
                                break;
                            case 17:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime orderDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            // DateTimeFormatter formatter = DateTimeFormatter
                                            //         .ofPattern("yyyy-MM-dd hh:mm:ss");
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedOrderDate = orderDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setOrderDate(formattedOrderDate);
                                            // Now you can use pickupDateDate as needed
                                        } else {
                                            deliveryOrderImportExcelRequest.setOrderDate(arrayList.get(i).toString());

                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Pickup Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 18:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime invoiceDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedPickupTime = invoiceDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setInvoiceDate(formattedPickupTime);
                                        } else {
                                            deliveryOrderImportExcelRequest.setInvoiceDate(arrayList.get(i).toString());
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Invoice Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 19:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime invoiceReceivingDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedPickupTime = invoiceReceivingDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setInvoiceReceivingDate(formattedPickupTime);
                                        } else {
                                            deliveryOrderImportExcelRequest.setInvoiceReceivingDate(arrayList.get(i).toString());
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Invoice Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 20:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String invoiceString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            invoiceString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            invoiceString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setInvoiceNo(invoiceString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for invoice at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 21:
                                deliveryOrderImportExcelRequest.setSaleOrderNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 22:
                                deliveryOrderImportExcelRequest.setDocumentNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 23:
                                deliveryOrderImportExcelRequest.setReferenceNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 24:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String otherNumberString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            otherNumberString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            otherNumberString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setOtherNumber(otherNumberString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for otherNumber at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 25:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String shipToString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            shipToString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            shipToString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setShipTo(shipToString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for ship to at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 26: {
                                deliveryOrderImportExcelRequest.setReceiverName(String.valueOf(arrayList.get(i)));
                                break;
                            }
                            case 27:
                                String regex = "\\s||[!@#$%^&*(),.?\\\":{}|<>]";
                                Pattern pattern = Pattern.compile(regex);
                                Matcher matcher = pattern.matcher(String.valueOf(arrayList.get(i)));
                                boolean matchFound = matcher.find();
                                if (matchFound) {
                                    deliveryOrderImportExcelRequest.setReceiverPhone(null);
                                } else {
                                    deliveryOrderImportExcelRequest.setReceiverPhone(String.valueOf(arrayList.get(i)));
                                }
                                break;
                            case 28:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    BigDecimal value = new BigDecimal(arrayList.get(i).toString());
                                    deliveryOrderImportExcelRequest.setDeliveryLatitude(value.doubleValue());
                                }
                                break;
                            case 29:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    BigDecimal value = new BigDecimal(arrayList.get(i).toString());
                                    deliveryOrderImportExcelRequest.setDeliveryLongtitude(value.doubleValue());
                                }
                                break;

                            case 30:
                                deliveryOrderImportExcelRequest.setDeliveryPoint(String.valueOf(arrayList.get(i)));
                                break;
                            case 31:
                                deliveryOrderImportExcelRequest.setRegion(String.valueOf(arrayList.get(i)));
                                break;
                            case 32:
                                deliveryOrderImportExcelRequest.setAreaOfResponsibility(String.valueOf(arrayList.get(i)));
                                break;
                            case 33:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String areaIdString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            areaIdString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            areaIdString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setAreaMasterId(areaIdString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for id area master ID at:\nRow: " + index
                                                    + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 34 :
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());
                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime pickupDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
                                            String formattedPickupTime = pickupDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setDueDate(formattedPickupTime);
                                        } else {
                                            deliveryOrderImportExcelRequest.setDueDate(arrayList.get(i).toString());
                                        }
                                        // Now you can use pickupDateTime as needed
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for DueDate at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i) );
                                }
                                break;
                            case 35:
                                deliveryOrderImportExcelRequest.setProductNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 36:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setSs(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 37:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setS(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 38:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setM(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 39:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setL(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 40:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 41:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 42:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 43:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxxxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 44:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxxxxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 45:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setSpecialSize(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 46:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    Double value = Double.valueOf(arrayList.get(i).toString());
                                    int roundedValue = (int) Math.round(value); // Làm tròn giá trị
                                    deliveryOrderImportExcelRequest.setLoadingBox(roundedValue);
                                }
                                break;
                            case 47:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    Double value = Double.valueOf(arrayList.get(i).toString());
                                    int roundedValue = (int) Math.round(value); // Làm tròn giá trị
                                    deliveryOrderImportExcelRequest.setLoadingPiece(roundedValue);
                                }
                                break;
                            case 48:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setUnit((int) Double.parseDouble(arrayList.get(i).toString()));

                                }
                                break;
                            case 49:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    String stringValue = String.valueOf((int) Math.round((Double) arrayList.get(i))); // Chuyển
                                    // đổi
                                    // thành
                                    // số
                                    // nguyên,
                                    // sau
                                    // đó
                                    // thành
                                    // chuỗi
                                    deliveryOrderImportExcelRequest.setTypeOfGood(stringValue);
                                }
                                break;
                            case 50:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingWeight(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 51:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingLiter(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 52:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingCbm(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 53:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingTarget(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 54:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setAmountOfMoney(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 55:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setSpecial(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 56:
                                deliveryOrderImportExcelRequest.setCod(String.valueOf(arrayList.get(i).toString()));
                                break;
                            case 57:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setPallet(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 58:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setInsurance(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 59:
                                deliveryOrderImportExcelRequest.setDeliveryNote(String.valueOf(arrayList.get(i)));
                                break;
                            case 60:
                                deliveryOrderImportExcelRequest.setDeliveryNote_2(String.valueOf(arrayList.get(i)));
                                break;
                            case 61:
                                deliveryOrderImportExcelRequest.setDeliveryNote_3(String.valueOf(arrayList.get(i)));
                                break;
                            case 62:
                                deliveryOrderImportExcelRequest.setDeliveryNote_4(String.valueOf(arrayList.get(i)));
                                break;
                            case 63:
                                deliveryOrderImportExcelRequest.setOriginShipment(String.valueOf(arrayList.get(i)));
                                break;

                        }
                    } catch (ClassCastException e) {
                        throw new AppValidationException("Invalid data type at:\nRow: " + index + "\nColumn: "
                                + map.get(i));
                    } catch (NumberFormatException e) {
                        throw new AppValidationException("Invalid number format at:\nRow: " + index + "\nColumn: "
                                + map.get(i));
                    }
                }
            } else {
                throw new AppValidationException("emptyTransportRequest " + index);
            }

            shipmentOrderImportExcelRequest.setDeliverOrderRequest(deliveryOrderImportExcelRequest);
            list.add(shipmentOrderImportExcelRequest);
        }
        return list;
    }

    public static List<ShipmentOrderImportExcelRequest> convertExcelToNewDeliveryOrder2(
            HashMap<Integer, ArrayList<Object>> dataExcel) {
//        ShipmentImportExcelRequest shipmentImportExcelRequest = null;
        List<ShipmentOrderImportExcelRequest> list = new ArrayList<>();
        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "Delivery Order Number");
        map.put(2, "ID:Company");
        map.put(3, "ID:Site");
        map.put(4, "ID:Sender");
        map.put(5, "Sender's name");
        map.put(6, "Status type");
        // map.put(7, "Delivery Status");
        map.put(7, "ID:Pickup");
        map.put(8, "Pickup latitude");
        map.put(9, "Pickup longitude");
        map.put(10, "Pickup Date");
        map.put(11, "Pickup note");
        map.put(12, "Document type1");
        map.put(13, "Document type2");
        map.put(14, "delivery type 1");
        map.put(15, "delivery type 2");
        map.put(16, "Calculation order");
        map.put(17, "Order Date");
        map.put(18, "Invoice Date");
        map.put(19, "Invoice receiving date");
        map.put(20, "Invoice_numbers");
        map.put(21, "sale order_numbers");
        map.put(22, "Document_numbers");
        map.put(23, "Reference_numbers");
        map.put(24, "other_numbers");
        map.put(25, "Ship-to");
        map.put(26, "Receiver's name");
        map.put(27, "Receiver's phone");
        map.put(28, "Delivery latitude");
        map.put(29, "Delivery longitude");
        map.put(30, "Delivery point");
        map.put(31, "Region");
        map.put(32, "Area of responsibility");
        map.put(33, "ID Area Master");
        map.put(34, "Duedate");
        map.put(35, "Product_no");
        map.put(36, "SS");
        map.put(37, "S");
        map.put(38, "M");
        map.put(39, "L");
        map.put(40, "XL");
        map.put(41, "2xL");
        map.put(42, "3xL");
        map.put(43, "4XL");
        map.put(44, "5xL");
        map.put(45, "Special  (Size)");
        map.put(46, "Loading box");
        map.put(47, "Loadding piece/pieces");
        map.put(48, "Unit");
        map.put(49, "Type of good");
        map.put(50, "Loading weight");
        map.put(51, "Loading liter");
        map.put(52, "Loading CBM");
        map.put(53, "Loading target %");
        map.put(54, "amount of money");
        map.put(55, "Special");
        map.put(56, "COD");
        map.put(57, "Pallet");
        map.put(58, "insurance");
        map.put(59, "Delivery Note 1");
        map.put(60, "Delivery Note 2");
        map.put(61, "Delivery Note 3");
        map.put(62, "Delivery Note 4");
        map.put(63, "Origin Shipment");



        Integer count=0;
        Integer startIndex = 0;
        ArrayList<Object> columnList = dataExcel.get(0);
        for (int i = 0; i < columnList.size(); i++) {
            if (columnList.get(i).equals("Delivery Order Number")) {
                startIndex = i;
                break;
            }
        }

        for (int index = 1; index <= dataExcel.size()-1; index++) {
            ArrayList<Object> arrayList = dataExcel.get(index);
            // arrayList = arrayList.subList(startIndex, arrayList.size());
            // System.out.println("============="+arrayList);
            DeliveryOrderImportExcelRequest deliveryOrderImportExcelRequest = new DeliveryOrderImportExcelRequest();
            ShipmentOrderImportExcelRequest shipmentOrderImportExcelRequest = new ShipmentOrderImportExcelRequest();

            if (arrayList != null && !arrayList.isEmpty() ) {
                // System.out.println("start index : " + startIndex);

                for (int i = startIndex; i < arrayList.size(); i++) {
                    count++;
                    if (!StringUtils.hasText(arrayList.get(20).toString()) && !StringUtils.hasText(arrayList.get(2).toString())) {
                        continue;
                    }


                    try {
                        switch (i-startIndex) {
                            case 0:
                                if (StringUtils.hasText(arrayList.get(i).toString())) {
                                    deliveryOrderImportExcelRequest.setDeliveryOrderNumber(String.valueOf(arrayList.get(i)));
                                }
                                break;
                            case 1:
                                deliveryOrderImportExcelRequest.setCompanyId(String.valueOf(arrayList.get(i)));
                                break;
                            case 2:
                                // Check if the Site ID is null or empty
                                if (!StringUtils.hasText(arrayList.get(i).toString())) {
                                    // If it's null or empty, you can handle it here
                                    // For example, you might skip this record or log a warning
                                    // You can decide based on your requirements
                                    // Here, we're skipping this record
                                    continue; // Skip processing this record and move to the next one
                                }
                                deliveryOrderImportExcelRequest.setBranchId(String.valueOf(arrayList.get(i)));
                                break;
                            case 3:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String senderIdString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            senderIdString = String.format("%.0f", arrayList.get(i));
                                            if (senderIdString.length()<11) {
                                                senderIdString = "0" + senderIdString;
                                            }
                                        } else {
                                            // If the value is not a double, directly set it
                                            senderIdString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setSenderId(senderIdString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for Sender ID at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i));
                                }
                                break;
                            case 4:
                                deliveryOrderImportExcelRequest.setSenderName(String.valueOf(arrayList.get(i)));
                                break;
                            //statustype
                            case 5:
                                deliveryOrderImportExcelRequest.setStatusType(String.valueOf(arrayList.get(i)));
                                break;
                            // case 6: 
                            //     String statusType = String.valueOf(arrayList.get(i-1));
                            //     if (statusType.equals("D")) {
                            //         deliveryOrderImportExcelRequest.setDeliveryStatus("D");
                            //     }else{
                            //         deliveryOrderImportExcelRequest.setDeliveryStatus("T"); 
                            //     }
                            //     break;
                            case 6:
                                deliveryOrderImportExcelRequest.setPickupId(String.valueOf(arrayList.get(i)));
                                break;
                            case 7:
                                // if (StringUtils.hasText(arrayList.get(i).toString())
                                //         && arrayList.get(i) instanceof Double) {
                                //     deliveryOrderImportExcelRequest
                                //             .setPickupLatitude(Double.valueOf(arrayList.get(i).toString()));
                                // }
                                // break;
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    BigDecimal value = new BigDecimal(arrayList.get(i).toString());
                                    deliveryOrderImportExcelRequest.setPickupLatitude(value.doubleValue());
                                }
                                break;
                            case 8:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    BigDecimal value = new BigDecimal(arrayList.get(i).toString());
                                    deliveryOrderImportExcelRequest.setPickupLongtitude(value.doubleValue());
                                }
                                break;
                            case 9:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime pickupDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            // DateTimeFormatter formatter = DateTimeFormatter
                                            //         .ofPattern("yyyy-MM-dd hh:mm:ss");
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedPickupTime = pickupDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setPickupTime(formattedPickupTime);
                                            // Now you can use pickupDateTime as needed
                                        } else {
                                            deliveryOrderImportExcelRequest.setPickupTime(arrayList.get(i).toString());

                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Pickup Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i));
                                }
                                break;
                            case 10:
                                deliveryOrderImportExcelRequest.setPickupNote(String.valueOf(arrayList.get(i)));
                                break;
                            case 11:
                                Object documentTypeObj = arrayList.get(i);
                                if (documentTypeObj != null) {
                                    if (documentTypeObj instanceof String) {
                                        String documentTypeString = (String) documentTypeObj;
                                        if (StringUtils.hasText(documentTypeString)) {
                                            try {
                                                Integer documentType = Integer.parseInt(documentTypeString);
                                                deliveryOrderImportExcelRequest.setDocumentType(documentType);
                                            } catch (NumberFormatException e) {
                                                System.err.println("Invalid document type: " + documentTypeString);
                                            }
                                        }
                                    } else if (documentTypeObj instanceof Double) {
                                        Double documentTypeDouble = (Double) documentTypeObj;
                                        Integer documentType = documentTypeDouble.intValue();
                                        deliveryOrderImportExcelRequest.setDocumentType(documentType);
                                    } else {
                                        System.err.println("Unsupported document type: " + documentTypeObj);
                                    }
                                }
                                break;

                            case 12:
                                Object documentType2Obj = arrayList.get(i);
                                if (documentType2Obj != null) {
                                    if (documentType2Obj instanceof String) {
                                        String documentType2String = (String) documentType2Obj;
                                        if (StringUtils.hasText(documentType2String)) {
                                            try {
                                                Integer documentType2 = Integer.parseInt(documentType2String);
                                                deliveryOrderImportExcelRequest.setDocumentType_2(documentType2);
                                            } catch (NumberFormatException e) {
                                                System.err.println("Invalid document type: " + documentType2String);
                                            }
                                        }
                                    } else if (documentType2Obj instanceof Double) {
                                        Double documentType2Double = (Double) documentType2Obj;
                                        Integer documentType2 = documentType2Double.intValue();
                                        deliveryOrderImportExcelRequest.setDocumentType_2(documentType2);
                                    } else {
                                        System.err.println("Unsupported document type: " + documentType2Obj);
                                    }
                                }
                                break;

                            case 13:
                                Object deliveryTypeObj = arrayList.get(i);
                                if (deliveryTypeObj != null) {
                                    if (deliveryTypeObj instanceof String) {
                                        String deliveryTypeString = (String) deliveryTypeObj;
                                        if (StringUtils.hasText(deliveryTypeString)) {
                                            try {
                                                Integer deliveryType = Integer.parseInt(deliveryTypeString);
                                                deliveryOrderImportExcelRequest.setDeliveryType(deliveryType);
                                            } catch (NumberFormatException e) {
                                                System.err.println("Invalid delivery type: " + deliveryTypeString);
                                            }
                                        }
                                    } else if (deliveryTypeObj instanceof Double) {
                                        Double deliveryTypeDouble = (Double) deliveryTypeObj;
                                        Integer deliveryType = deliveryTypeDouble.intValue();
                                        deliveryOrderImportExcelRequest.setDeliveryType(deliveryType);
                                    } else {
                                        System.err.println("Unsupported delivery type: " + deliveryTypeObj);
                                    }
                                }
                                break;

                            case 14:
                                Object deliveryType2Obj = arrayList.get(i);
                                if (deliveryType2Obj != null) {
                                    if (deliveryType2Obj instanceof String) {
                                        String deliveryType2String = (String) deliveryType2Obj;
                                        if (StringUtils.hasText(deliveryType2String)) {
                                            try {
                                                Integer deliveryType2 = Integer.parseInt(deliveryType2String);
                                                deliveryOrderImportExcelRequest.setDeliveryType_2(deliveryType2);
                                            } catch (NumberFormatException e) {
                                                System.err.println("Invalid delivery type: " + deliveryType2String);
                                            }
                                        }
                                    } else if (deliveryType2Obj instanceof Double) {
                                        Double deliveryType2Double = (Double) deliveryType2Obj;
                                        Integer deliveryType2 = deliveryType2Double.intValue();
                                        deliveryOrderImportExcelRequest.setDeliveryType_2(deliveryType2);
                                    } else {
                                        System.err.println("Unsupported delivery type: " + deliveryType2Obj);
                                    }
                                }
                                break;
                            case 15:
                                deliveryOrderImportExcelRequest.setCalculationOrder(String.valueOf(arrayList.get(i)));
                                break;
                            case 16:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime orderDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            // DateTimeFormatter formatter = DateTimeFormatter
                                            //         .ofPattern("yyyy-MM-dd hh:mm:ss");
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedOrderDate = orderDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setOrderDate(formattedOrderDate);
                                            // Now you can use pickupDateDate as needed
                                        } else {
                                            deliveryOrderImportExcelRequest.setOrderDate(arrayList.get(i).toString());

                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Pickup Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 17:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime invoiceDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedPickupTime = invoiceDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setInvoiceDate(formattedPickupTime);
                                        } else {
                                            deliveryOrderImportExcelRequest.setInvoiceDate(arrayList.get(i).toString());
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Invoice Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 18:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());

                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime invoiceReceivingDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedPickupTime = invoiceReceivingDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setInvoiceReceivingDate(formattedPickupTime);
                                        } else {
                                            deliveryOrderImportExcelRequest.setInvoiceReceivingDate(arrayList.get(i).toString());
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for Invoice Time at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 19:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String invoiceString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            invoiceString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            invoiceString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setInvoiceNo(invoiceString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for invoice at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 20:
                                deliveryOrderImportExcelRequest.setSaleOrderNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 21:
                                deliveryOrderImportExcelRequest.setDocumentNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 22:
                                deliveryOrderImportExcelRequest.setReferenceNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 23:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String otherNumberString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            otherNumberString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            otherNumberString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setOtherNumber(otherNumberString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for otherNumber at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 24:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String shipToString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            shipToString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            shipToString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setShipTo(shipToString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for ship to at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 25: {
                                deliveryOrderImportExcelRequest.setReceiverName(String.valueOf(arrayList.get(i)));
                                break;
                            }
                            case 26:
                                String regex = "\\s||[!@#$%^&*(),.?\\\":{}|<>]";
                                Pattern pattern = Pattern.compile(regex);
                                Matcher matcher = pattern.matcher(String.valueOf(arrayList.get(i)));
                                boolean matchFound = matcher.find();
                                if (matchFound) {
                                    deliveryOrderImportExcelRequest.setReceiverPhone(null);
                                } else {
                                    deliveryOrderImportExcelRequest.setReceiverPhone(String.valueOf(arrayList.get(i)));
                                }
                                break;
                            case 27:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {

                                    deliveryOrderImportExcelRequest
                                            .setDeliveryLatitude(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 28:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {

                                    deliveryOrderImportExcelRequest
                                            .setDeliveryLongtitude(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;

                            case 29:
                                deliveryOrderImportExcelRequest.setDeliveryPoint(String.valueOf(arrayList.get(i)));
                                break;
                            case 30:
                                deliveryOrderImportExcelRequest.setRegion(String.valueOf(arrayList.get(i)));
                                break;
                            case 31:
                                deliveryOrderImportExcelRequest.setAreaOfResponsibility(String.valueOf(arrayList.get(i)));
                                break;
                            case 32:
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        String areaIdString;

                                        if (arrayList.get(i) instanceof Double) {
                                            // Convert the number to a string without scientific notation
                                            areaIdString = String.format("%.0f", arrayList.get(i));
                                        } else {
                                            // If the value is not a double, directly set it
                                            areaIdString = String.valueOf(arrayList.get(i));
                                        }

                                        deliveryOrderImportExcelRequest.setAreaMasterId(areaIdString);
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid number
                                    throw new AppValidationException(
                                            "Invalid number format for id area master ID at:\nRow: " + index
                                                    + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 33 :
                                try {
                                    if (StringUtils.hasText(arrayList.get(i).toString())) {
                                        if (arrayList.get(i) instanceof Double) {
                                            double excelSerial = Double.parseDouble(arrayList.get(i).toString());
                                            // Adjust the base date to Excel's base date (January 1, 1900)
                                            LocalDateTime baseDateTime = LocalDateTime.of(1899, 12, 30, 0, 0, 0);

                                            // Calculate the number of days and convert to seconds
                                            long daysInSeconds = (long) (excelSerial * 24 * 60 * 60);
                                            LocalDateTime pickupDateTime = baseDateTime.plusSeconds(daysInSeconds);
                                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
                                            String formattedPickupTime = pickupDateTime.format(formatter);
                                            deliveryOrderImportExcelRequest.setDueDate(formattedPickupTime);
                                        } else {
                                            deliveryOrderImportExcelRequest.setDueDate(arrayList.get(i).toString());
                                        }
                                        // Now you can use pickupDateTime as needed
                                    }
                                } catch (NumberFormatException e) {
                                    // Handle the exception if the value is not a valid double
                                    throw new AppValidationException(
                                            "Invalid number format for DueDate at:\nRow: " + index + "\nColumn: "
                                                    + map.get(i)    );
                                }
                                break;
                            case 34:
                                deliveryOrderImportExcelRequest.setProductNo(String.valueOf(arrayList.get(i)));
                                break;
                            case 35:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setSs(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 36:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setS(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 37:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setM(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 38:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setL(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 39:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 40:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 41:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 42:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxxxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 43:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest.setXxxxxl(((Double) arrayList.get(i)).intValue());
                                }
                                break;
                            case 44:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setSpecialSize(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 45:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    Double value = Double.valueOf(arrayList.get(i).toString());
                                    int roundedValue = (int) Math.round(value); // Làm tròn giá trị
                                    if (roundedValue >= 0) {
                                        deliveryOrderImportExcelRequest.setLoadingBox(roundedValue);
                                    } else {
                                        deliveryOrderImportExcelRequest.setLoadingBox(0); // Không cho phép giá trị âm
                                    }
                                } else {
                                    deliveryOrderImportExcelRequest.setLoadingBox(0);
                                }
                                break;
                            case 46:
                                if (StringUtils.hasText(arrayList.get(i).toString()) && arrayList.get(i) instanceof Double) {
                                    Double value = Double.valueOf(arrayList.get(i).toString());
                                    int roundedValue = (int) Math.round(value); // Làm tròn giá trị
                                    if (roundedValue >= 0) {
                                        deliveryOrderImportExcelRequest.setLoadingPiece(roundedValue);
                                    } else {
                                        deliveryOrderImportExcelRequest.setLoadingPiece(0); // Không cho phép giá trị âm
                                    }
                                } else {
                                    deliveryOrderImportExcelRequest.setLoadingPiece(0);
                                }
                                break;
                            case 47:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setUnit((int) Double.parseDouble(arrayList.get(i).toString()));

                                }
                                break;
                            case 48:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    String stringValue = String.valueOf((int) Math.round((Double) arrayList.get(i))); // Chuyển
                                    // đổi
                                    // thành
                                    // số
                                    // nguyên,
                                    // sau
                                    // đó
                                    // thành
                                    // chuỗi
                                    deliveryOrderImportExcelRequest.setTypeOfGood(stringValue);
                                }
                                break;
                            case 49:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingWeight(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 50:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingLiter(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 51:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingCbm(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 52:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setLoadingTarget(Double.valueOf(arrayList.get(i).toString()));
                                }else{
                                    deliveryOrderImportExcelRequest
                                            .setLoadingTarget(0.0);
                                }
                                break;
                            case 53:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setAmountOfMoney(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 54:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setSpecial(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 55:
                                deliveryOrderImportExcelRequest.setCod(String.valueOf(arrayList.get(i).toString()));
                                break;
                            case 56:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setPallet(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 57:
                                if (StringUtils.hasText(arrayList.get(i).toString())
                                        && arrayList.get(i) instanceof Double) {
                                    deliveryOrderImportExcelRequest
                                            .setInsurance(Double.valueOf(arrayList.get(i).toString()));
                                }
                                break;
                            case 58:
                                deliveryOrderImportExcelRequest.setDeliveryNote(String.valueOf(arrayList.get(i)));
                                break;
                            case 59:
                                deliveryOrderImportExcelRequest.setDeliveryNote_2(String.valueOf(arrayList.get(i)));
                                break;
                            case 60:
                                deliveryOrderImportExcelRequest.setDeliveryNote_3(String.valueOf(arrayList.get(i)));
                                break;
                            case 61:
                                deliveryOrderImportExcelRequest.setDeliveryNote_4(String.valueOf(arrayList.get(i)));
                                break;
                            case 62:
                                deliveryOrderImportExcelRequest.setOriginShipment(String.valueOf(arrayList.get(i)));
                                break;

                        }
                    } catch (ClassCastException e) {
                        throw new AppValidationException("Invalid data type at:\nRow: " + index + "\nColumn: "
                                + map.get(i)    );
                    } catch (NumberFormatException e) {
                        throw new AppValidationException("Invalid number format at:\nRow: " + index + "\nColumn: "
                                + map.get(i)    );
                    }
                }
            } else {
                throw new AppValidationException("emptyTransportRequest " + index   );
            }

            shipmentOrderImportExcelRequest.setDeliverOrderRequest(deliveryOrderImportExcelRequest);
            // if (shipmentImportExcelRequest != null) {
            //     shipmentImportExcelRequest.setShipmentStatus(ShipmentStatus.PLANING.getValue());
            //     shipmentOrderImportExcelRequest.setShipmentRequest(shipmentImportExcelRequest);
            //     shipmentImportExcelRequest = null;
            // }

            list.add(shipmentOrderImportExcelRequest);
        }
        return list;
    }

}

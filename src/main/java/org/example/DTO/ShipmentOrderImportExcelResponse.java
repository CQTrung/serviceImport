package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShipmentOrderImportExcelResponse {
    private String errorDetail;
    private String importKey;
    DeliveryOrderImportExcelRequest deliveryOrder;
}
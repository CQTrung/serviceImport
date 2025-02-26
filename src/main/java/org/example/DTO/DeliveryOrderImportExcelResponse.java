package org.example.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryOrderImportExcelResponse {
    Integer total;
    List<ShipmentOrderImportExcelResponse> importfail;
    List<ShipmentOrderImportExcelResponse> updateRecord;
    List<ShipmentOrderImportExcelResponse> insertRecord;
}

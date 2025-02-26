package org.example.repository;

import org.example.entity.DtbRttDeliveryOrder;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryOrderRepository extends JpaRepositoryBase<DtbRttDeliveryOrder,Integer>,
        JpaSpecificationExecutor<DtbRttDeliveryOrder> {
    @Query(value = "SELECT status FROM dtb_rtt_delivery_order do WHERE "
            +"do.delivery_order_key LIKE :deliveryOrderKey ", nativeQuery = true)
    List<Object> countDeliveryOrderByKey(
            @Param("deliveryOrderKey") String deliveryOrderKey);
    @Query(value = "SELECT * FROM dtb_rtt_delivery_order deo " +
            "WHERE deo.delivery_order_number = :deliveryOrderNumber " +
            "AND deo.status  in (-1,14,17) " +
            "AND deo.is_deleted = 0", nativeQuery = true)
    Optional<DtbRttDeliveryOrder> checkDeliveryOrderUpdateimport(@Param("deliveryOrderNumber") String deliveryOrderNumber);

    @Query(value = "SELECT branch_id FROM dtb_rtt_delivery_order deo " +
            "WHERE deo.invoice_no = :invoice " +
            "AND deo.is_deleted = 0", nativeQuery = true)
    List<String> countInvoiceAndIsDeletedFalse(@Param("invoice") String invoice);
}
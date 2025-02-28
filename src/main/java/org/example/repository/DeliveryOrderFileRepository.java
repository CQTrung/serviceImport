package org.example.repository;


import org.example.entity.DtbRttDeliveryOrderFile;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryOrderFileRepository extends JpaRepositoryBase<DtbRttDeliveryOrderFile, Integer>, JpaSpecificationExecutor<DtbRttDeliveryOrderFile> {

   @Query(value = " SELECT * FROM  dtb_rtt_delivery_order_file where status = 1 ", nativeQuery = true)
   List<DtbRttDeliveryOrderFile> findByStatus(int Status);

  // List<DtbRttRole> findAllByUsername(@Param("userName") String userName);
}

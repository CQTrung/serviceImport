package org.example.repository;

import org.example.entity.DtbRttDeliveryOrder;
import org.example.entity.DtbRttDeliveryOrderTemp;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryOrderTempRepository extends JpaRepositoryBase<DtbRttDeliveryOrderTemp,Integer>,
        JpaSpecificationExecutor<DtbRttDeliveryOrderTemp> {}
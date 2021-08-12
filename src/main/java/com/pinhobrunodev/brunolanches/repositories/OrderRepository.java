package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 1")
    List<Order> ShowAllPendingOrders();
}

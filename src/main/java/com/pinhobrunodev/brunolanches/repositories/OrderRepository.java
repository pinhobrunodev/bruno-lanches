package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 1")
    Page<Order> PageshowAllPendingOrders(Pageable pageable);

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 0")
    List<Order> showAllDeliveredOrders();

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 1")
    List<Order> showAllPendingOrders();

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 2")
    List<Order> showAllInProgressOrders();

    @Query("SELECT obj FROM Order obj JOIN  FETCH  obj.user u WHERE  u.id = :id AND obj.status = 2")
    List<Order> showAllInProgressOrdersByUserId(Long id);

    @Query("SELECT obj FROM Order obj JOIN  FETCH  obj.user u WHERE  u.id = :id AND obj.status = 0")
    List<Order> showAllDeliveredOrdersByUserId(Long id);

    @Query("SELECT obj FROM Order obj JOIN  FETCH  obj.user u WHERE  u.id = :id AND obj.status = 1")
    List<Order> showAllPendingOrdersByUserId(Long id);

    //*
    @Query("SELECT  obj  FROM Order obj JOIN FETCH  obj.user u WHERE  u.id = :id")
    List<Order> showAllOrdersByUserId(Long id);

    @Query("SELECT  obj FROM Order obj JOIN  FETCH  obj.driver d WHERE d.id = :id AND obj.status=0 ")
    List<Order> showAllDeliveredOrdersByDriverId(Long id);

    @Query("SELECT obj FROM Order obj JOIN  FETCH  obj.driver d WHERE d.id = :id AND obj.status= 2 AND obj.isInProgress=true")
    List<Order> showAllInProgressOrdersByDriverId(Long id);

}

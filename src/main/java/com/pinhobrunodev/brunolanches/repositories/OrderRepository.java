package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 1")
    Page<Order> PageshowAllPendingOrders(Pageable pageable);

    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 1")
    List<Order> showAllPendingOrders();


    @Query("SELECT DISTINCT obj FROM Order obj WHERE  obj.status = 0")
    List<Order> showAllDeliveredOrders();

    @Query("SELECT  DISTINCT  obj FROM User obj JOIN FETCH  obj.orders  WHERE obj.id = :id")
    List<User> showAllOrdersByUserId(Long id);

    @Query("SELECT  DISTINCT  obj FROM Driver obj JOIN  FETCH  obj.orders o WHERE obj.id = :id AND o.status=0 ")
    List<Driver> showAllDeliveredOrdersByDriverId(Long id);

    @Query("SELECT  DISTINCT  obj FROM Driver obj JOIN  FETCH  obj.orders o WHERE obj.id = :id AND o.status=1 ")
    List<Driver> showAllPendingOrdersByDriverId(Long id);

}

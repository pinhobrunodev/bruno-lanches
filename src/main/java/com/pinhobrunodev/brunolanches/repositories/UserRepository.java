package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);



    @Query("SELECT  DISTINCT  obj FROM User obj JOIN FETCH  obj.orders o WHERE obj.id = :id AND o.status = 1")
    List<User> showAllPendingOrdersByUserId(Long id);

    @Query("SELECT  DISTINCT  obj FROM User obj JOIN FETCH  obj.orders o WHERE obj.id = :id AND o.status = 0")
    List<User> showAllDeliveredOrdersByUserId(Long id);


}

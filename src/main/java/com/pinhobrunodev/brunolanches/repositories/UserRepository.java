package com.pinhobrunodev.brunolanches.repositories;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByName(String name);



}

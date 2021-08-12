package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.order.OrderDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.entities.enums.OrderStatus;
import com.pinhobrunodev.brunolanches.repositories.DriverRepository;
import com.pinhobrunodev.brunolanches.repositories.OrderRepository;
import com.pinhobrunodev.brunolanches.repositories.ProductRepository;
import com.pinhobrunodev.brunolanches.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private OrderRepository repository;
    @Autowired
    private ProductRepository productRepository;


    @Transactional
    public void save(OrderDTO dto) {
        Order entity = new Order();
        repository.save(copyDtoToEntity(entity, dto));
    }



    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> findAll(){
        return repository.findAll().stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }


    // Auxiliary methods

    public Order copyDtoToEntity(Order entity, OrderDTO dto) {
        entity.setUser(userRepository.getById(dto.getUser_id()));
        entity.setStatus(OrderStatus.PENDING);
        for (ProductDTO x : dto.getItems()) {
            Product aux = productRepository.getById(x.getId());
            entity.getItems().add(aux);
        }
        return entity;
    }


}

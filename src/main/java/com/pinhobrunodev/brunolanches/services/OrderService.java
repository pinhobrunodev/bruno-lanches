package com.pinhobrunodev.brunolanches.services;

import com.pinhobrunodev.brunolanches.dto.order.OrderDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.dto.user.ShowUserOrderDTO;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.Product;
import com.pinhobrunodev.brunolanches.entities.User;
import com.pinhobrunodev.brunolanches.entities.enums.OrderStatus;
import com.pinhobrunodev.brunolanches.repositories.DriverRepository;
import com.pinhobrunodev.brunolanches.repositories.OrderRepository;
import com.pinhobrunodev.brunolanches.repositories.ProductRepository;
import com.pinhobrunodev.brunolanches.repositories.UserRepository;
import com.pinhobrunodev.brunolanches.services.exceptions.ResourceNotFoundException;
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

    // Can be Admin " All Pending Orders" screen.
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showAllPendingOrders() {
        return repository.showAllPendingOrders().stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Can be Admin "All IN PROGRESS Orders" screen.
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showAllInProgressOrders() {
        return repository.showAllInProgressOrders().stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Can be Admin "All Delivered Orders" screen.
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> showAllDeliveredOrders() {
        return repository.showAllDeliveredOrders().stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Can be Admin "Filter All Orders (PENDING AND DELIVERED) by User ID" screen
    @Transactional(readOnly = true)
    public List<ShowUserOrderDTO> showAllOrdersByUserId(Long id) {
        return repository.showAllOrdersByUserId(id).stream().map(ShowUserOrderDTO::new).collect(Collectors.toList());
    }

    // Can be Admin "All orders of all Users"
    @Transactional(readOnly = true)
    public List<ShowOrderInfoDTO> findAll() {
        return repository.findAll().stream().map(ShowOrderInfoDTO::new).collect(Collectors.toList());
    }

    // Auxiliary methods

    public Order copyDtoToEntity(Order entity, OrderDTO dto) {
        entity.setUser(userRepository.getById(dto.getUser_id()));
        entity.setStatus(OrderStatus.PENDING);
        entity.setInProgress(Boolean.FALSE);
        for (ProductDTO x : dto.getItems()) {
            Product aux = productRepository.getById(x.getId());
            entity.getItems().add(aux);
        }
        return entity;
    }


}

package com.pinhobrunodev.brunolanches.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowOrderInfoDTO {

    @JsonIgnore
    public final Double DRIVER_PERCENTAGE = 0.8;

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime moment;
    private OrderStatus status;
    private String client_name;
    private String client_phone;
    private String client_address;
    private List<ProductDTO> items = new ArrayList<>();

    public ShowOrderInfoDTO() {

    }

    public ShowOrderInfoDTO(Order entity) {
        id = entity.getId();
        moment = entity.getMoment();
        status = entity.getStatus();
        client_name = entity.getUser().getName();
        client_phone = entity.getUser().getPhone();
        client_address = entity.getUser().getAddress();
        entity.getItems().forEach(x -> items.add(new ProductDTO(x)));
    }

    public Double getTotalToPay() {
        double sum = 0;
        for (ProductDTO dto : items) {
            sum += dto.getPrice();
        }

        return Math.round(sum * 100) / 100d;
    }

    public Double getDriverAmount() {
        double driverAmount = 0;
        double aux = 0;
        aux = getTotalToPay() * DRIVER_PERCENTAGE;
        driverAmount = getTotalToPay() - aux;
        return Math.round(driverAmount * 100) / 100d;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getClient_phone() {
        return client_phone;
    }

    public void setClient_phone(String client_phone) {
        this.client_phone = client_phone;
    }

    public String getClient_address() {
        return client_address;
    }

    public void setClient_address(String client_address) {
        this.client_address = client_address;
    }

    public List<ProductDTO> getItems() {
        return items;
    }
}

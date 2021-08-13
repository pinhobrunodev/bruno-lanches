package com.pinhobrunodev.brunolanches.dto.driver;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.entities.Order;
import com.pinhobrunodev.brunolanches.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ShowDriverOrderDTO {

    private List<ShowOrderInfoDTO> orders = new ArrayList<>();

    public ShowDriverOrderDTO() {
    }

    public ShowDriverOrderDTO(Driver entity) {
        entity.getOrders().forEach(x->orders.add(new ShowOrderInfoDTO(x)));
    }



    public List<ShowOrderInfoDTO> getOrders() {
        return orders;
    }
}

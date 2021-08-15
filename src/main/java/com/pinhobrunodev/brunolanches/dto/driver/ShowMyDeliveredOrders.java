package com.pinhobrunodev.brunolanches.dto.driver;

import com.pinhobrunodev.brunolanches.dto.order.OrderDTO;
import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;

import java.util.ArrayList;
import java.util.List;

public class ShowMyDeliveredOrders {

    private List<ShowOrderInfoDTO> orders = new ArrayList<>();

    public ShowMyDeliveredOrders() {
    }

    public ShowMyDeliveredOrders(Driver entity) {
        entity.getOrders().forEach(x -> orders.add(new ShowOrderInfoDTO(x)));
    }


    public List<ShowOrderInfoDTO> getOrders() {
        return orders;
    }
}

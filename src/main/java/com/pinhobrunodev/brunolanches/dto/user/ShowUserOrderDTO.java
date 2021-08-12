package com.pinhobrunodev.brunolanches.dto.user;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ShowUserOrderDTO {

    private List<ShowOrderInfoDTO> orders = new ArrayList<>();

    public ShowUserOrderDTO(){
    }

    public ShowUserOrderDTO(User entity){
        entity.getOrders().forEach(x->orders.add(new ShowOrderInfoDTO(x)));
    }

    public List<ShowOrderInfoDTO> getOrders() {
        return orders;
    }
}

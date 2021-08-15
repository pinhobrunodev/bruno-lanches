package com.pinhobrunodev.brunolanches.dto.user;

import com.pinhobrunodev.brunolanches.dto.order.ShowOrderInfoDTO;
import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;
import com.pinhobrunodev.brunolanches.entities.User;

import java.util.ArrayList;
import java.util.List;

public class ShowUserOrderDTO {

    private List<ShowOrderInfoDTO> orders = new ArrayList<>();

    public ShowUserOrderDTO() {
    }


    public ShowUserOrderDTO(User entity) {
        entity.getOrders().forEach(x -> orders.add(new ShowOrderInfoDTO(x)));
    }

    /*
            ALL PENDING TOTAL ORDER PRICE
    public Double getTotalToPay() {
        double sum = 0;
        for (ShowOrderInfoDTO dto : orders) {
            for (ProductDTO productDTO : dto.getItems()) {
                sum += productDTO.getPrice();
            }
        }
        return sum;
    }
*/

    public List<ShowOrderInfoDTO> getOrders() {
        return orders;
    }
}

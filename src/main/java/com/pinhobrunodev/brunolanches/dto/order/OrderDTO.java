package com.pinhobrunodev.brunolanches.dto.order;

import com.pinhobrunodev.brunolanches.dto.product.ProductDTO;

import java.util.ArrayList;
import java.util.List;

public class OrderDTO {
    private Long id;
    private Long user_id;
    private final List<ProductDTO> items = new ArrayList<>();

    public OrderDTO() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public List<ProductDTO> getItems() {
        return items;
    }
}

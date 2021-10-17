package com.pinhobrunodev.brunolanches.dto.client;


import com.pinhobrunodev.brunolanches.entities.Client;

import java.time.LocalDate;

public class ShowClientInfoDTO {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private LocalDate date;
    private String address;

    public ShowClientInfoDTO() {

    }

    public ShowClientInfoDTO(Client entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        email = entity.getEmail();
        date = entity.getDate();
        address = entity.getAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

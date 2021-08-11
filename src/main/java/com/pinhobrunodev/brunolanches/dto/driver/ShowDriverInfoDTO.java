package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.entities.Driver;

import java.time.LocalDate;

public class ShowDriverInfoDTO {

    private Long id;
    private String name;
    private String phone;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    public ShowDriverInfoDTO() {
    }

    public ShowDriverInfoDTO(Driver entity) {
        id = entity.getId();
        name = entity.getName();
        phone = entity.getPhone();
        date = entity.getDate();
        email = entity.getEmail();
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
}

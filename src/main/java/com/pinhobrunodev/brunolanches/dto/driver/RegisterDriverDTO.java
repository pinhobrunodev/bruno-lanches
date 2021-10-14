package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.dto.role.RoleDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RegisterDriverDTO {

    private Long id;
    @NotBlank(message = "Mandatory field")
    @Size(min = 6, max = 20, message = "Name must be between 6 and 20 digits")
    private String name;
    @NotBlank(message = "Mandatory field")
    @Size(min = 17, max = 17, message = "Phone number must be like this: +55(75)94562-2234")
    private String phone;
    @NotBlank(message = "Mandatory field")
    private String email;
    @NotBlank(message = "Mandatory field")
    @Size(min = 4, max = 15, message = "It must contain at least 4 characters and no more than 15 characters")
    private String password;
    @NotBlank(message = "Mandatory field")
    private String cpf;
    @NotNull(message = "Mandatory field")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;
    private final List<RoleDTO> roles = new ArrayList<>();

    public RegisterDriverDTO() {
    }

    public RegisterDriverDTO(Driver entity) {
        id = entity.getId();
        name = entity.getName();
        password = entity.getPassword();
        phone = entity.getPhone();
        cpf = entity.getCpf();
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }
}

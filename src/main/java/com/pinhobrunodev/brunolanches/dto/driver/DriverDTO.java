package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.dto.role.RoleDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DriverDTO {
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
    private String cpf;
    @NotNull(message = "Mandatory field")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;
    private final List<RoleDTO> roles = new ArrayList<>();

    public DriverDTO() {

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

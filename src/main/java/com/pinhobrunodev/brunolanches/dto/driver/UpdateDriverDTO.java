package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.entities.Driver;
import org.springframework.security.core.parameters.P;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class UpdateDriverDTO {

    private Long id;
    @NotBlank(message = "Mandatory field")
    @Size(min = 6, max = 20, message = "Name must be between 6 and 20 digits")
    private String name;
    @NotBlank(message = "Mandatory field")
    @Size(min = 15, max = 20, message = "Phone number must be like this: 55(71)4562-2234")
    @Pattern(regexp = "^([0-9]{2})?(\\([0-9]{2})\\)([0-9]{3}|[0-9]{4})-[0-9]{4}$\n",message = "Phone number must be like this: 55(71)4562-2234")
    private String phone;
    @NotBlank(message = "Mandatory field")
    @Pattern(regexp = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$\n",message = "Please,put a valid email")
    private String email;
    @NotBlank(message = "Mandatory field")
    @Size(min = 4, max = 15, message = "It must contain at least 4 characters and no more than 15 characters")
    @Pattern(regexp = "^[a-zA-Z]\\w{3,14}$\n",message = "The password's first character must be a letter  and no other characters than letters, numbers and the underscore may be used")
    private String password;
    @NotBlank(message = "Mandatory field")
    @Pattern(regexp = "(^\\d{3}\\x2E\\d{3}\\x2E\\d{3}\\x2D\\d{2}$)\n",message = "Invalid CPF")
    private String cpf;
    @NotBlank(message = "Mandatory field")
    @PastOrPresent(message = "Date must be past or present")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate date;

    public UpdateDriverDTO() {
    }

    public UpdateDriverDTO(Driver entity) {
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
}

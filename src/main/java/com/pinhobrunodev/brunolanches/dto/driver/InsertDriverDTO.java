package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.dto.role.RoleDTO;
import com.pinhobrunodev.brunolanches.dto.user.UserDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.services.validation.driver.DriverInsertValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@DriverInsertValid
public class InsertDriverDTO extends UserDTO {

    private String password;

    public InsertDriverDTO(){

    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

package com.pinhobrunodev.brunolanches.dto.driver;

import com.pinhobrunodev.brunolanches.dto.client.ClientDTO;
import com.pinhobrunodev.brunolanches.services.validation.driver.DriverInsertValid;

@DriverInsertValid
public class InsertDriverDTO extends ClientDTO {

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

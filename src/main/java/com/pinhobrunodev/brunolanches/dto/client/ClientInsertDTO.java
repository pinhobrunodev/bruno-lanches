package com.pinhobrunodev.brunolanches.dto.client;


import com.pinhobrunodev.brunolanches.services.validation.client.ClientInsertValid;

@ClientInsertValid
public class ClientInsertDTO extends ClientDTO {

    private String password;

    public ClientInsertDTO(){
    super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

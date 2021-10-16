package com.pinhobrunodev.brunolanches.dto.user;


import com.pinhobrunodev.brunolanches.services.validation.user.UserInsertValid;

@UserInsertValid
public class UserInsertDTO extends  UserDTO{

    private String password;

    public UserInsertDTO(){
    super();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

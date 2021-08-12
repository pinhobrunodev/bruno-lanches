package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.entities.Driver;

import java.time.LocalDate;

public class TakeOrderDTO {

    private Long driver_id;

    public TakeOrderDTO() {
    }


    public Long getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(Long driver_id) {
        this.driver_id = driver_id;
    }
}

package com.pinhobrunodev.brunolanches.dto.driver;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pinhobrunodev.brunolanches.dto.user.UserDTO;
import com.pinhobrunodev.brunolanches.entities.Driver;
import com.pinhobrunodev.brunolanches.services.validation.driver.DriverUpdateValid;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@DriverUpdateValid
public class UpdateDriverDTO extends UserDTO {

}

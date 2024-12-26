package com.restaurant.api.rest.v1.vo;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestVO {

    @NotBlank
    @Size(max = 80)
    public String name;

    @NotBlank
    @Email
    @Size(max = 50)
    public String email;

    @Size(max = 100)
    public String password;

}

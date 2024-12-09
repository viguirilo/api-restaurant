package com.restaurant.api.rest.v1.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestVO {

    public String name;
    public String email;
    public String password;

}

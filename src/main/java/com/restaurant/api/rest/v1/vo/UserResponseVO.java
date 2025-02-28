package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserResponseVO {

    private Long id;
    private String fullname;
    private String username;
    private String email;
    private String languageCode;
    private String currencyCode;
    private String timezone;
    private LocalDateTime creationDate;

    public UserResponseVO(User user) {
        BeanUtils.copyProperties(user, this);
    }

}

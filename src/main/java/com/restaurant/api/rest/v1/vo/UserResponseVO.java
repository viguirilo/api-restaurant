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

    public Long id;
    public String name;
    public String email;
    public LocalDateTime creationDate;

    public UserResponseVO(User user) {
        BeanUtils.copyProperties(user, this);
    }

}

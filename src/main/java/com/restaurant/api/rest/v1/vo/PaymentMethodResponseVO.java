package com.restaurant.api.rest.v1.vo;

import com.restaurant.api.rest.v1.entity.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PaymentMethodResponseVO {

    public Long id;
    public String description;

    public PaymentMethodResponseVO(PaymentMethod paymentMethod) {
        BeanUtils.copyProperties(paymentMethod, this);
    }

}

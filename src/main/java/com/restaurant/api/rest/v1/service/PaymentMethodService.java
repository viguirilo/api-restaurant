package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.PaymentMethod;
import com.restaurant.api.rest.v1.repository.PaymentMethodRepository;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import com.restaurant.api.rest.v1.vo.PaymentMethodResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethod save(PaymentMethodRequestVO paymentMethodRequestVO) {
        return paymentMethodRepository.save(new PaymentMethod(paymentMethodRequestVO));
    }

    public List<PaymentMethod> findAll() {
        return paymentMethodRepository.findAll();
    }

    public PaymentMethodResponseVO findById(Long id) {
        Optional<PaymentMethod> paymentMethodById = paymentMethodRepository.findById(id);
        return paymentMethodById.map(PaymentMethodResponseVO::new).orElse(null);
    }

    public PaymentMethod update(Long id, PaymentMethodRequestVO paymentMethodRequestVO) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            BeanUtils.copyProperties(paymentMethodRequestVO, paymentMethod, "id");
            return paymentMethodRepository.save(paymentMethod);
        } else return null;
    }

    public PaymentMethod delete(Long id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            paymentMethodRepository.delete(paymentMethod);
            return paymentMethod;
        } else return null;
    }

}

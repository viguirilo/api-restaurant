package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.PaymentMethod;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.PaymentMethodRepository;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import com.restaurant.api.rest.v1.vo.PaymentMethodResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final Logger logger = Logger.getLogger(PaymentMethodService.class.getName());

    public PaymentMethodResponseVO save(PaymentMethodRequestVO paymentMethodRequestVO) {
        PaymentMethod paymentMethod = paymentMethodRepository.save(new PaymentMethod(paymentMethodRequestVO));
        logger.info(paymentMethod + " CREATED SUCCESSFULLY");
        return new PaymentMethodResponseVO(paymentMethod);
    }

    public List<PaymentMethodResponseVO> findAll() {
        List<PaymentMethod> paymentMethods = paymentMethodRepository.findAll();
        if (!paymentMethods.isEmpty()) {
            logger.info("FOUND " + paymentMethods.size() + " PAYMENT METHODS");
            return paymentMethods.stream().map(PaymentMethodResponseVO::new).toList();
        } else {
            logger.warning("PAYMENT METHODS NOT FOUND");
            throw new EntityNotFoundException("Payment methods not found");
        }
    }

    public PaymentMethodResponseVO findById(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> {
            logger.warning("PAYMENT METHOD NOT FOUND");
            return new EntityNotFoundException("The payment method requested was not found");
        });
        logger.info(paymentMethod + " FOUND SUCCESSFULLY");
        return new PaymentMethodResponseVO(paymentMethod);
    }

    public PaymentMethodResponseVO update(Long id, PaymentMethodRequestVO paymentMethodRequestVO) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: PAYMENT METHOD " + id + " NOT FOUND");
            return new EntityNotFoundException("The payment method requested was not found");
        });
        BeanUtils.copyProperties(paymentMethodRequestVO, paymentMethod, "id");
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        logger.info(paymentMethod + " UPDATED SUCCESSFULLY");
        return new PaymentMethodResponseVO(paymentMethod);
    }

    public PaymentMethodResponseVO delete(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT DELETE: PAYMENT METHOD " + id + " NOT FOUND");
            return new EntityNotFoundException("The payment method requested was not found");
        });
        paymentMethodRepository.delete(paymentMethod);
        logger.info(paymentMethod + " DELETED SUCCESSFULLY");
        return new PaymentMethodResponseVO(paymentMethod);
    }

}

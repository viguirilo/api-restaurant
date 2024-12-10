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
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;
    private final Logger logger = Logger.getLogger(PaymentMethodService.class.getName());

    public PaymentMethodResponseVO save(PaymentMethodRequestVO paymentMethodRequestVO) {
        logger.info("Creating a new Payment Method");
        return new PaymentMethodResponseVO(paymentMethodRepository.save(new PaymentMethod(paymentMethodRequestVO)));
    }

    public List<PaymentMethodResponseVO> findAll() {
        logger.info("Returning Payment Methods, if exists");
        return paymentMethodRepository.findAll().stream().map(PaymentMethodResponseVO::new).toList();
    }

    public PaymentMethodResponseVO findById(Long id) {
        logger.info("Returning Payment Method id = " + id + ", if exists");
        Optional<PaymentMethod> paymentMethodById = paymentMethodRepository.findById(id);
        return paymentMethodById.map(PaymentMethodResponseVO::new).orElse(null);
    }

    public PaymentMethodResponseVO update(Long id, PaymentMethodRequestVO paymentMethodRequestVO) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            BeanUtils.copyProperties(paymentMethodRequestVO, paymentMethod, "id");
            logger.info("Updating Payment Method id = " + id);
            return new PaymentMethodResponseVO(paymentMethodRepository.save(paymentMethod));
        } else {
            logger.info("Couldn't update Payment Method id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public PaymentMethodResponseVO delete(Long id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            paymentMethodRepository.delete(paymentMethod);
            logger.info("Deleting Payment Method id = " + id);
            return new PaymentMethodResponseVO(paymentMethod);
        } else {
            logger.info("Couldn't delete Payment Method id = " + id + " because it doesn't exists");
            return null;
        }
    }

}

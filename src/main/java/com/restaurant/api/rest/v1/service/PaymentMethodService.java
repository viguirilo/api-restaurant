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
            return null;
        }
    }

    public PaymentMethodResponseVO findById(Long id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            logger.info(paymentMethod + " FOUND SUCCESSFULLY");
            return new PaymentMethodResponseVO(paymentMethod);
        } else {
            logger.warning("PAYMENT METHOD NOT FOUND");
            return null;
        }
    }

    public PaymentMethodResponseVO update(Long id, PaymentMethodRequestVO paymentMethodRequestVO) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            BeanUtils.copyProperties(paymentMethodRequestVO, paymentMethod, "id");
            paymentMethod = paymentMethodRepository.save(paymentMethod);
            logger.info(paymentMethod + " UPDATED SUCCESSFULLY");
            return new PaymentMethodResponseVO(paymentMethod);
        } else {
            logger.warning("CAN NOT UPDATE: PAYMENT METHOD " + id + " NOT FOUND");
            return null;
        }
    }

    public PaymentMethodResponseVO delete(Long id) {
        Optional<PaymentMethod> paymentMethodOptional = paymentMethodRepository.findById(id);
        if (paymentMethodOptional.isPresent()) {
            PaymentMethod paymentMethod = paymentMethodOptional.get();
            paymentMethodRepository.delete(paymentMethod);
            logger.info(paymentMethod + " DELETED SUCCESSFULLY");
            return new PaymentMethodResponseVO(paymentMethod);
        } else {
            logger.warning("CAN NOT DELETE: PAYMENT METHOD " + id + " NOT FOUND");
            return null;
        }
    }

}

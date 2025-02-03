package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.PaymentMethod;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.PaymentMethodRepository;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import com.restaurant.api.rest.v1.vo.PaymentMethodResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentMethodService {

    private final PaymentMethodRepository paymentMethodRepository;

    @Transactional
    public PaymentMethodResponseVO save(PaymentMethodRequestVO paymentMethodRequestVO) {
        PaymentMethod paymentMethod = paymentMethodRepository.save(new PaymentMethod(paymentMethodRequestVO));
        log.info("SAVE: {} CREATED SUCCESSFULLY", paymentMethod);
        return new PaymentMethodResponseVO(paymentMethod);
    }

    public Page<PaymentMethodResponseVO> findAll(Pageable pageable) {
        Page<PaymentMethod> paymentMethods = paymentMethodRepository.findAll(pageable);
        if (!paymentMethods.isEmpty()) {
            log.info("FIND ALL: FOUND {} PAYMENT METHODS", paymentMethods.getTotalElements());
            return new PageImpl<>(paymentMethods.stream().map(PaymentMethodResponseVO::new).toList(), pageable, paymentMethods.getTotalElements());
        } else {
            log.error("FIND ALL: PAYMENT METHODS NOT FOUND");
            throw new EntityNotFoundException("Payment methods not found");
        }
    }

    public PaymentMethodResponseVO findById(Long id) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: PAYMENT METHOD ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The payment method requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", paymentMethod);
        return new PaymentMethodResponseVO(paymentMethod);
    }

    @Transactional
    public PaymentMethodResponseVO update(Long id, PaymentMethodRequestVO paymentMethodRequestVO) {
        PaymentMethod paymentMethod = paymentMethodRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: PAYMENT METHOD ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The payment method requested was not found");
        });
        BeanUtils.copyProperties(paymentMethodRequestVO, paymentMethod, "id");
        paymentMethod = paymentMethodRepository.save(paymentMethod);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", paymentMethod);
        return new PaymentMethodResponseVO(paymentMethod);
    }

    @Transactional
    public void delete(Long id) {
        try {
            paymentMethodRepository.deleteById(id);
            paymentMethodRepository.flush();
            log.info("UPDATE: PAYMENT METHOD ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("UPDATE: THE REQUESTED PAYMENT METHOD ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("PAYMENT METHOD = " + id);
        }
    }

}

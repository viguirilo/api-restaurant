package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.PaymentMethod;
import com.restaurant.api.rest.v1.service.PaymentMethodService;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import com.restaurant.api.rest.v1.vo.PaymentMethodResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/paymentMethods")
@RequiredArgsConstructor
public class PaymentMethodController {

    private final PaymentMethodService paymentMethodService;

    // TODO(colocar este método para retornar um VO)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public PaymentMethodResponseVO save(@RequestBody PaymentMethodRequestVO paymentMethodRequestVO) {
        return new PaymentMethodResponseVO(paymentMethodService.save(paymentMethodRequestVO));
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PaymentMethodResponseVO>> findAll() {
        return ResponseEntity.ok().body(paymentMethodService.findAll());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentMethodResponseVO> findById(@PathVariable Long id) {
        PaymentMethodResponseVO paymentMethodResponseVO = paymentMethodService.findById(id);
        if (paymentMethodResponseVO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(paymentMethodResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentMethodResponseVO> update(@PathVariable Long id, @RequestBody PaymentMethodRequestVO paymentMethodRequestVO) {
        PaymentMethod paymentMethod = paymentMethodService.update(id, paymentMethodRequestVO);
        if (paymentMethod == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(new PaymentMethodResponseVO(paymentMethod));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            PaymentMethod paymentMethod = paymentMethodService.delete(id);
            if (paymentMethod == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
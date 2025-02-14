package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.controller.openapi.PaymentMethodControllerOpenApi;
import com.restaurant.api.rest.v1.service.PaymentMethodService;
import com.restaurant.api.rest.v1.vo.PaymentMethodRequestVO;
import com.restaurant.api.rest.v1.vo.PaymentMethodResponseVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/paymentMethods")
@RequiredArgsConstructor
public class PaymentMethodController implements PaymentMethodControllerOpenApi {

    private final PaymentMethodService paymentMethodService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentMethodResponseVO> save(@RequestBody @Valid PaymentMethodRequestVO paymentMethodRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentMethodService.save(paymentMethodRequestVO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<PaymentMethodResponseVO>> findAll(Pageable pageable) {
        Page<PaymentMethodResponseVO> paymentMethodResponseVOS = paymentMethodService.findAll(pageable);
        return ResponseEntity.ok().body(paymentMethodResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentMethodResponseVO> findById(@PathVariable Long id) {
        PaymentMethodResponseVO paymentMethodResponseVO = paymentMethodService.findById(id);
        return ResponseEntity.ok().body(paymentMethodResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PaymentMethodResponseVO> update(@PathVariable Long id, @RequestBody @Valid PaymentMethodRequestVO paymentMethodRequestVO) {
        PaymentMethodResponseVO paymentMethodResponseVO = paymentMethodService.update(id, paymentMethodRequestVO);
        return ResponseEntity.ok().body(paymentMethodResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        paymentMethodService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

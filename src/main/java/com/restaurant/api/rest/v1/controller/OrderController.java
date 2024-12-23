package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.OrderService;
import com.restaurant.api.rest.v1.vo.OrderRequestVO;
import com.restaurant.api.rest.v1.vo.OrderResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseVO> save(@RequestBody OrderRequestVO orderRequestVO) {
        OrderResponseVO orderResponseVO = orderService.save(orderRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponseVO);
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderResponseVO>> findAll() {
        List<OrderResponseVO> orderResponseVOS = orderService.findAll();
        return ResponseEntity.ok().body(orderResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseVO> findById(@PathVariable Long id) {
        OrderResponseVO orderResponseVO = orderService.findById(id);
        return ResponseEntity.ok().body(orderResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderResponseVO> update(@PathVariable Long id, @RequestBody OrderRequestVO orderRequestVO) {
        OrderResponseVO orderResponseVO = orderService.update(id, orderRequestVO);
        return ResponseEntity.ok().body(orderResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

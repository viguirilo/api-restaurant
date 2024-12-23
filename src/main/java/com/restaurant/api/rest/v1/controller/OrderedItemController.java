package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.OrderedItemService;
import com.restaurant.api.rest.v1.vo.OrderedItemRequestVO;
import com.restaurant.api.rest.v1.vo.OrderedItemResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/itemsOrdered")
@RequiredArgsConstructor
public class OrderedItemController {

    private final OrderedItemService orderedItemService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderedItemResponseVO> save(@RequestBody OrderedItemRequestVO orderedItemRequestVO) {
        OrderedItemResponseVO orderedItemResponseVO = orderedItemService.save(orderedItemRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderedItemResponseVO);
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderedItemResponseVO>> findAll() {
        List<OrderedItemResponseVO> orderedItemResponseVOS = orderedItemService.findAll();
        return ResponseEntity.ok().body(orderedItemResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderedItemResponseVO> findById(@PathVariable Long id) {
        OrderedItemResponseVO orderedItemResponseVO = orderedItemService.findById(id);
        return ResponseEntity.ok().body(orderedItemResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<OrderedItemResponseVO> update(@PathVariable Long id, @RequestBody OrderedItemRequestVO orderedItemRequestVO) {
        OrderedItemResponseVO orderedItemResponseVO = orderedItemService.update(id, orderedItemRequestVO);
        return ResponseEntity.ok().body(orderedItemResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderedItemService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

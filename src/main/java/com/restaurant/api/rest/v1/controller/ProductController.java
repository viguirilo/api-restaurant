package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.ProductService;
import com.restaurant.api.rest.v1.vo.ProductRequestVO;
import com.restaurant.api.rest.v1.vo.ProductResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ProductResponseVO> save(@RequestBody ProductRequestVO permissionRequestVO) {
        ProductResponseVO productResponseVO = productService.save(permissionRequestVO);
        if (productResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(productResponseVO);
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ProductResponseVO>> findAll() {
        List<ProductResponseVO> productResponseVOS = productService.findAll();
        if (productResponseVOS == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(productResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseVO> findById(@PathVariable Long id) {
        ProductResponseVO productResponseVO = productService.findById(id);
        if (productResponseVO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(productResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseVO> update(@PathVariable Long id, @RequestBody ProductRequestVO productRequestVO) {
        ProductResponseVO productResponseVO = productService.update(id, productRequestVO);
        if (productResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(productResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            ProductResponseVO productResponseVO = productService.delete(id);
            if (productResponseVO == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

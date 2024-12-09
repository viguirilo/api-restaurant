package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.Product;
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

    // TODO(colocar este método para retornar um VO)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Product> save(@RequestBody ProductRequestVO permissionRequestVO) {
        Product product = productService.save(permissionRequestVO);
        if (product == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(product);
    }

    // TODO(colocar paginação neste endpoint)
    // TODO(colocar este método para retornar um VO)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Product>> findAll() {
        return ResponseEntity.ok().body(productService.findAll());
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

    // TODO(colocar este método para retornar um VO)
    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductRequestVO productRequestVO) {
        Product product = productService.update(id, productRequestVO);
        if (product == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(product);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Product product = productService.delete(id);
            if (product == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

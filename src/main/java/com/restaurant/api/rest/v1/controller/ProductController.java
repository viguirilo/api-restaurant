package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.controller.openapi.ProductControllerOpenApi;
import com.restaurant.api.rest.v1.service.ProductService;
import com.restaurant.api.rest.v1.vo.ProductRequestVO;
import com.restaurant.api.rest.v1.vo.ProductResponseVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/products")
@RequiredArgsConstructor
public class ProductController implements ProductControllerOpenApi {

    private final ProductService productService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseVO> save(@RequestBody @Valid ProductRequestVO permissionRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(permissionRequestVO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<ProductResponseVO>> findAll(Pageable pageable) {
        Page<ProductResponseVO> productResponseVOS = productService.findAll(pageable);
        return ResponseEntity.ok().body(productResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseVO> findById(@PathVariable Long id) {
        ProductResponseVO productResponseVO = productService.findById(id);
        return ResponseEntity.ok().body(productResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ProductResponseVO> update(@PathVariable Long id, @RequestBody @Valid ProductRequestVO productRequestVO) {
        ProductResponseVO productResponseVO = productService.update(id, productRequestVO);
        return ResponseEntity.ok().body(productResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

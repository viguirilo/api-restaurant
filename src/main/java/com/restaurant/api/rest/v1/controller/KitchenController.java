package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.controller.openapi.KitchenControllerOpenApi;
import com.restaurant.api.rest.v1.service.KitchenService;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import com.restaurant.api.rest.v1.vo.KitchenResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/kitchens")
@RequiredArgsConstructor
public class KitchenController implements KitchenControllerOpenApi {

    private final KitchenService kitchenService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<KitchenResponseVO> save(@RequestBody KitchenRequestVO kitchenRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(kitchenService.save(kitchenRequestVO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<KitchenResponseVO>> findAll(Pageable pageable) {
        Page<KitchenResponseVO> kitchenResponseVOS = kitchenService.findAll(pageable);
        return ResponseEntity.ok().body(kitchenResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<KitchenResponseVO> findById(@PathVariable Long id) {
        KitchenResponseVO kitchenResponseVO = kitchenService.findById(id);
        return ResponseEntity.ok().body(kitchenResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<KitchenResponseVO> update(@PathVariable Long id, @RequestBody KitchenRequestVO kitchenRequestVO) {
        KitchenResponseVO kitchenResponseVO = kitchenService.update(id, kitchenRequestVO);
        return ResponseEntity.ok().body(kitchenResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        kitchenService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

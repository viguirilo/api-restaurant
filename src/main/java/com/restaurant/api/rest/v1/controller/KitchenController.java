package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.KitchenService;
import com.restaurant.api.rest.v1.vo.KitchenRequestVO;
import com.restaurant.api.rest.v1.vo.KitchenResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/kitchens")
@RequiredArgsConstructor
public class KitchenController {

    private final KitchenService kitchenService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<KitchenResponseVO> save(@RequestBody KitchenRequestVO kitchenRequestVO) {
        return ResponseEntity.ok().body(kitchenService.save(kitchenRequestVO));
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<KitchenResponseVO>> findAll() {
        List<KitchenResponseVO> kitchenResponseVOS = kitchenService.findAll();
        if (kitchenResponseVOS == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(kitchenResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<KitchenResponseVO> findById(@PathVariable Long id) {
        KitchenResponseVO kitchenResponseVO = kitchenService.findById(id);
        if (kitchenResponseVO == null) return ResponseEntity.notFound().build();
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
        if (kitchenResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(kitchenResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            KitchenResponseVO kitchenResponseVO = kitchenService.delete(id);
            if (kitchenResponseVO == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

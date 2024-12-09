package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.Kitchen;
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

    // TODO(colocar este método para retornar um VO)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Kitchen save(@RequestBody KitchenRequestVO kitchenRequestVO) {
        return kitchenService.save(kitchenRequestVO);
    }

    // TODO(colocar paginação neste endpoint)
    // TODO(colocar este método para retornar um VO)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Kitchen>> findAll() {
        return ResponseEntity.ok().body(kitchenService.findAll());
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

    // TODO(colocar este método para retornar um VO)
    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Kitchen> update(@PathVariable Long id, @RequestBody KitchenRequestVO kitchenRequestVO) {
        Kitchen kitchen = kitchenService.update(id, kitchenRequestVO);
        if (kitchen == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(kitchen);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Kitchen kitchen = kitchenService.delete(id);
            if (kitchen == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    //    Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH.


}

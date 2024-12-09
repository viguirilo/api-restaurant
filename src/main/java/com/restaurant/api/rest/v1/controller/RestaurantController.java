package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.RestaurantService;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import com.restaurant.api.rest.v1.vo.RestaurantResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RestaurantResponseVO> save(@RequestBody RestaurantRequestVO restaurantRequestVO) {
        RestaurantResponseVO restaurantResponseVO = restaurantService.save(restaurantRequestVO);
        if (restaurantResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(restaurantResponseVO);
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RestaurantResponseVO>> findAll() {
        List<RestaurantResponseVO> restaurantResponseVOS = restaurantService.findAll();
        if (restaurantResponseVOS.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(restaurantResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestaurantResponseVO> findById(@PathVariable Long id) {
        RestaurantResponseVO restaurantResponseVO = restaurantService.findById(id);
        if (restaurantResponseVO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(restaurantResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestaurantResponseVO> update(@PathVariable Long id, @RequestBody RestaurantRequestVO restaurantRequestVO) {
        RestaurantResponseVO restaurantResponseVO = restaurantService.update(id, restaurantRequestVO);
        if (restaurantResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(restaurantResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            RestaurantResponseVO restaurantResponseVO = restaurantService.delete(id);
            if (restaurantResponseVO == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.controller.openapi.RestaurantControllerOpenApi;
import com.restaurant.api.rest.v1.service.RestaurantService;
import com.restaurant.api.rest.v1.vo.RestaurantRequestVO;
import com.restaurant.api.rest.v1.vo.RestaurantResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/restaurants")
@RequiredArgsConstructor
public class RestaurantController implements RestaurantControllerOpenApi {

    private final RestaurantService restaurantService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestaurantResponseVO> save(@RequestBody RestaurantRequestVO restaurantRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.save(restaurantRequestVO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<RestaurantResponseVO>> findAll(Pageable pageable) {
        Page<RestaurantResponseVO> restaurantResponseVOS = restaurantService.findAll(pageable);
        return ResponseEntity.ok().body(restaurantResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<RestaurantResponseVO> findById(@PathVariable Long id) {
        RestaurantResponseVO restaurantResponseVO = restaurantService.findById(id);
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
        return ResponseEntity.ok().body(restaurantResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        restaurantService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

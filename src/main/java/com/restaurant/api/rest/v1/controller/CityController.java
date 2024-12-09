package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.City;
import com.restaurant.api.rest.v1.service.CityService;
import com.restaurant.api.rest.v1.vo.CityRequestVO;
import com.restaurant.api.rest.v1.vo.CityResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<City> save(@RequestBody CityRequestVO cityRequestVO) {
        City city = cityService.save(cityRequestVO);
        if (city == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(city);
    }

    // TODO(colocar paginação neste endpoint)
    // TODO(colocar este método para retornar um VO)
    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<City>> findAll() {
        return ResponseEntity.ok().body(cityService.findAll());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CityResponseVO> findById(@PathVariable Long id) {
        CityResponseVO cityResponseVO = cityService.findById(id);
        if (cityResponseVO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(cityResponseVO);
    }

    // TODO(colocar este método para retornar um VO)
    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH.)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<City> update(@PathVariable Long id, @RequestBody CityRequestVO cityRequestVO) {
        City city = cityService.update(id, cityRequestVO);
        if (city == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(city);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            City city = cityService.delete(id);
            if (city == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

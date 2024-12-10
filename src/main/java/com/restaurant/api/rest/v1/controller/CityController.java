package com.restaurant.api.rest.v1.controller;

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
@RequestMapping("/rest/v1/cities")
@RequiredArgsConstructor
public class CityController {

    private final CityService cityService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CityResponseVO> save(@RequestBody CityRequestVO cityRequestVO) {
        CityResponseVO cityResponseVO = cityService.save(cityRequestVO);
        if (cityResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(cityResponseVO);
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CityResponseVO>> findAll() {
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

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CityResponseVO> update(@PathVariable Long id, @RequestBody CityRequestVO cityRequestVO) {
        CityResponseVO cityResponseVO = cityService.update(id, cityRequestVO);
        if (cityResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(cityResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            CityResponseVO cityResponseVO = cityService.delete(id);
            if (cityResponseVO == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

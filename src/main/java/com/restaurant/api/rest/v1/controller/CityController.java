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
    public ResponseEntity<CityResponseVO> save(@RequestBody CityRequestVO cityRequestVO) {
        CityResponseVO cityResponseVO = cityService.save(cityRequestVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(cityResponseVO);
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CityResponseVO>> findAll() {
        List<CityResponseVO> cityResponseVOS = cityService.findAll();
        return ResponseEntity.ok().body(cityResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CityResponseVO> findById(@PathVariable Long id) {
        CityResponseVO cityResponseVO = cityService.findById(id);
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
        return ResponseEntity.ok().body(cityResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            cityService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

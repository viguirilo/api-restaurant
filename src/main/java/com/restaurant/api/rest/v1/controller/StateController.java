package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.StateService;
import com.restaurant.api.rest.v1.vo.StateRequestVO;
import com.restaurant.api.rest.v1.vo.StateResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/states")
@RequiredArgsConstructor
public class StateController {

    private final StateService stateService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateResponseVO> save(@RequestBody StateRequestVO stateRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(stateService.save(stateRequestVO));
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StateResponseVO>> findAll() {
        List<StateResponseVO> stateResponseVOS = stateService.findAll();
        return ResponseEntity.ok().body(stateResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateResponseVO> findById(@PathVariable Long id) {
        StateResponseVO stateResponseVO = stateService.findById(id);
        return ResponseEntity.ok().body(stateResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH.)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateResponseVO> update(@PathVariable Long id, @RequestBody StateRequestVO stateRequestVO) {
        StateResponseVO stateResponseVO = stateService.update(id, stateRequestVO);
        return ResponseEntity.ok().body(stateResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            stateService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

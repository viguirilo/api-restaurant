package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.State;
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

    // TODO(colocar este método para retornar um VO)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public State save(@RequestBody StateRequestVO stepRequestVO) {
        return stateService.save(stepRequestVO);
    }

    // TODO(colocar paginação neste endpoint)
    // TODO(colocar este método para retornar um VO)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<State>> findAll() {
        return ResponseEntity.ok().body(stateService.findAll());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<StateResponseVO> findById(@PathVariable Long id) {
        StateResponseVO stateResponseVO = stateService.findById(id);
        if (stateResponseVO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(stateResponseVO);
    }

    // TODO(colocar este método para retornar um VO)
    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH.)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<State> update(@PathVariable Long id, @RequestBody StateRequestVO stateRequestVO) {
        State state = stateService.update(id, stateRequestVO);
        if (state == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(state);
    }

    // TODO(colocar este método para retornar um VO)
    @DeleteMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            State state = stateService.delete(id);
            if (state == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.Group;
import com.restaurant.api.rest.v1.service.GroupService;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // TODO(colocar este método para retornar um VO)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public Group save(@RequestBody GroupRequestVO groupRequestVO) {
        return groupService.save(groupRequestVO);
    }

    // TODO(colocar paginação neste endpoint)
    // TODO(colocar este método para retornar um VO)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Group>> findAll() {
        return ResponseEntity.ok().body(groupService.findAll());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GroupResponseVO> findById(@PathVariable Long id) {
        GroupResponseVO groupResponseVO = groupService.findById(id);
        if (groupResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(groupResponseVO);
    }

    // TODO(colocar este método para retornar um VO)
    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Group> update(@PathVariable Long id, @RequestBody GroupRequestVO groupRequestVO) {
        Group group = groupService.update(id, groupRequestVO);
        if (group == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(group);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Group group = groupService.delete(id);
            if (group == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

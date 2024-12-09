package com.restaurant.api.rest.v1.controller;

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

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GroupResponseVO> save(@RequestBody GroupRequestVO groupRequestVO) {
        return ResponseEntity.ok().body(groupService.save(groupRequestVO));
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupResponseVO>> findAll() {
        List<GroupResponseVO> groupResponseVOS = groupService.findAll();
        if (groupResponseVOS.isEmpty()) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(groupResponseVOS);
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

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GroupResponseVO> update(@PathVariable Long id, @RequestBody GroupRequestVO groupRequestVO) {
        GroupResponseVO groupResponseVO = groupService.update(id, groupRequestVO);
        if (groupResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(groupResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            GroupResponseVO groupResponseVO = groupService.delete(id);
            if (groupResponseVO == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}

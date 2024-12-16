package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.service.GroupService;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<GroupResponseVO> save(@RequestBody GroupRequestVO groupRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(groupRequestVO));
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GroupResponseVO>> findAll() {
        List<GroupResponseVO> groupResponseVOS = groupService.findAll();
        return ResponseEntity.ok().body(groupResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GroupResponseVO> findById(@PathVariable Long id) {
        GroupResponseVO groupResponseVO = groupService.findById(id);
        return ResponseEntity.ok().body(groupResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GroupResponseVO> update(@PathVariable Long id, @RequestBody GroupRequestVO groupRequestVO) {
        GroupResponseVO groupResponseVO = groupService.update(id, groupRequestVO);
        return ResponseEntity.ok().body(groupResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

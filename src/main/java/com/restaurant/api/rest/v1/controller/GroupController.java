package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.controller.openapi.GroupControllerOpenApi;
import com.restaurant.api.rest.v1.service.GroupService;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/v1/groups")
@RequiredArgsConstructor
public class GroupController implements GroupControllerOpenApi {

    private final GroupService groupService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<GroupResponseVO> save(@RequestBody @Valid GroupRequestVO groupRequestVO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.save(groupRequestVO));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<GroupResponseVO>> findAll(Pageable pageable) {
        Page<GroupResponseVO> groupResponseVOS = groupService.findAll(pageable);
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
    public ResponseEntity<GroupResponseVO> update(@PathVariable Long id, @RequestBody @Valid GroupRequestVO groupRequestVO) {
        GroupResponseVO groupResponseVO = groupService.update(id, groupRequestVO);
        return ResponseEntity.ok().body(groupResponseVO);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

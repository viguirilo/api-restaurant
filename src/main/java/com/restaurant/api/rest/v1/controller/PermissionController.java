package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.Permission;
import com.restaurant.api.rest.v1.service.PermissionService;
import com.restaurant.api.rest.v1.vo.PermissionRequestVO;
import com.restaurant.api.rest.v1.vo.PermissionResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rest/v1/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public PermissionResponseVO save(@RequestBody PermissionRequestVO permissionRequestVO) {
        return new PermissionResponseVO(permissionService.save(permissionRequestVO));
    }

    // TODO(colocar paginação neste endpoint)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PermissionResponseVO>> findAll() {
        return ResponseEntity.ok().body(permissionService.findAll());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PermissionResponseVO> findById(@PathVariable Long id) {
        PermissionResponseVO permissionResponseVO = permissionService.findById(id);
        if (permissionResponseVO == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok().body(permissionResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<PermissionResponseVO> update(@PathVariable Long id, @RequestBody PermissionRequestVO permissionRequestVO) {
        Permission permission = permissionService.update(id, permissionRequestVO);
        if (permission == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(new PermissionResponseVO(permission));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            Permission permission = permissionService.delete(id);
            if (permission == null) return ResponseEntity.notFound().build();
            else return ResponseEntity.noContent().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

}
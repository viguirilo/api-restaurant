package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Permission;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.PermissionRepository;
import com.restaurant.api.rest.v1.vo.PermissionRequestVO;
import com.restaurant.api.rest.v1.vo.PermissionResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    @Transactional
    public PermissionResponseVO save(PermissionRequestVO permissionRequestVO) {
        Permission permission = permissionRepository.save(new Permission(permissionRequestVO));
        log.info("SAVE: {} CREATED SUCCESSFULLY", permission);
        return new PermissionResponseVO(permission);
    }

    public Page<PermissionResponseVO> findAll(Pageable pageable) {
        Page<Permission> permissions = permissionRepository.findAll(pageable);
        if (!permissions.isEmpty()) {
            log.info("FIND ALL: FOUND {} PERMISSIONS", permissions.getTotalElements());
            return new PageImpl<>(permissions.stream().map(PermissionResponseVO::new).toList(), pageable, permissions.getTotalElements());
        } else {
            log.error("FIND ALL: PERMISSIONS NOT FOUND");
            throw new EntityNotFoundException("Permissions not found");
        }
    }

    public PermissionResponseVO findById(Long id) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: PERMISSION ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The permission requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", permission);
        return new PermissionResponseVO(permission);
    }

    @Transactional
    public PermissionResponseVO update(Long id, PermissionRequestVO permissionRequestVO) {
        Permission permission = permissionRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: PERMISSION ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The permission requested was not found");
        });
        BeanUtils.copyProperties(permissionRequestVO, permission, "id");
        permission = permissionRepository.save(permission);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", permission);
        return new PermissionResponseVO(permission);
    }

    @Transactional
    public void delete(Long id) {
        try {
            permissionRepository.deleteById(id);
            permissionRepository.flush();
            log.info("DELETE: PERMISSION ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED PERMISSION ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("PERMISSION = " + id);
        }
    }

}

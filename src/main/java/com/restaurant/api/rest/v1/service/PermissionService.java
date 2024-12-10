package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Permission;
import com.restaurant.api.rest.v1.repository.PermissionRepository;
import com.restaurant.api.rest.v1.vo.PermissionRequestVO;
import com.restaurant.api.rest.v1.vo.PermissionResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;
    private final Logger logger = Logger.getLogger(PermissionService.class.getName());

    public Permission save(PermissionRequestVO permissionRequestVO) {
        logger.info("Creating a new Permission");
        return permissionRepository.save(new Permission(permissionRequestVO));
    }

    public List<PermissionResponseVO> findAll() {
        logger.info("Returning Permissions, if exists");
        return permissionRepository.findAll().stream().map(PermissionResponseVO::new).toList();
    }

    public PermissionResponseVO findById(Long id) {
        logger.info("Returning Permission id = " + id + ", if exists");
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        return permissionOptional.map(PermissionResponseVO::new).orElse(null);
    }

    public Permission update(Long id, PermissionRequestVO permissionRequestVO) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            BeanUtils.copyProperties(permissionRequestVO, permission, "id");
            logger.info("Updating Permission id = " + id);
            return permissionRepository.save(permission);
        } else {
            logger.info("Couldn't update Permission id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public Permission delete(Long id) {
        Optional<Permission> permissionOptionalOptional = permissionRepository.findById(id);
        if (permissionOptionalOptional.isPresent()) {
            Permission permission = permissionOptionalOptional.get();
            permissionRepository.delete(permission);
            logger.info("Deleting Permission id = " + id);
            return permission;
        } else {
            logger.info("Couldn't delete Permission id = " + id + " because it doesn't exists");
            return null;
        }
    }

}

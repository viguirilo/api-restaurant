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

    public PermissionResponseVO save(PermissionRequestVO permissionRequestVO) {
        Permission permission = permissionRepository.save(new Permission(permissionRequestVO));
        logger.info(permission + " CREATED SUCCESSFULLY");
        return new PermissionResponseVO(permission);
    }

    public List<PermissionResponseVO> findAll() {
        List<Permission> permissions = permissionRepository.findAll();
        if (!permissions.isEmpty()) {
            logger.info("FOUND " + permissions.size() + " CITIES");
            return permissions.stream().map(PermissionResponseVO::new).toList();
        } else {
            logger.warning("PERMISSIONS NOT FOUND");
            return null;
        }
    }

    public PermissionResponseVO findById(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            logger.info(permission + " FOUND SUCCESSFULLY");
            return new PermissionResponseVO(permission);
        } else {
            logger.warning("CITY NOT FOUND");
            return null;
        }
    }

    public PermissionResponseVO update(Long id, PermissionRequestVO permissionRequestVO) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            BeanUtils.copyProperties(permissionRequestVO, permission, "id");
            permission = permissionRepository.save(permission);
            logger.info(permission + " UPDATED SUCCESSFULLY");
            return new PermissionResponseVO(permission);
        } else {
            logger.warning("CAN NOT UPDATE: PERMISSION " + id + " NOT FOUND");
            return null;
        }
    }

    public PermissionResponseVO delete(Long id) {
        Optional<Permission> permissionOptionalOptional = permissionRepository.findById(id);
        if (permissionOptionalOptional.isPresent()) {
            Permission permission = permissionOptionalOptional.get();
            permissionRepository.delete(permission);
            logger.info(permission + " DELETED SUCCESSFULLY");
            return new PermissionResponseVO(permission);
        } else {
            logger.warning("CAN NOT DELETE: PERMISSION " + id + " NOT FOUND");
            return null;
        }
    }

}

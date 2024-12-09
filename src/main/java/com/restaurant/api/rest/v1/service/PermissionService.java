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

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final PermissionRepository permissionRepository;

    public Permission save(PermissionRequestVO permissionRequestVO) {
        return permissionRepository.save(new Permission(permissionRequestVO));
    }

    public List<Permission> findAll() {
        return permissionRepository.findAll();
    }

    public PermissionResponseVO findById(Long id) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        return permissionOptional.map(PermissionResponseVO::new).orElse(null);
    }

    public Permission update(Long id, PermissionRequestVO permissionRequestVO) {
        Optional<Permission> permissionOptional = permissionRepository.findById(id);
        if (permissionOptional.isPresent()) {
            Permission permission = permissionOptional.get();
            BeanUtils.copyProperties(permissionRequestVO, permission, "id");
            return permissionRepository.save(permission);
        } else return null;
    }

    public Permission delete(Long id) {
        Optional<Permission> permissionOptionalOptional = permissionRepository.findById(id);
        if (permissionOptionalOptional.isPresent()) {
            Permission permission = permissionOptionalOptional.get();
            permissionRepository.delete(permission);
            return permission;
        } else return null;
    }

}

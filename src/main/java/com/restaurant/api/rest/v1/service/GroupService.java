package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Group;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.GroupRepository;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
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
public class GroupService {

    private final GroupRepository groupRepository;

    //    TODO(ver como criar permissions diretamente dentro do save)
    @Transactional
    public GroupResponseVO save(GroupRequestVO groupRequestVO) {
        Group group = groupRepository.save(new Group(groupRequestVO));
        log.info("SAVE: {} CREATED SUCCESSFULLY", group);
        return new GroupResponseVO(group);
    }

    public Page<GroupResponseVO> findAll(Pageable pageable) {
        Page<Group> groups = groupRepository.findAll(pageable);
        if (!groups.isEmpty()) {
            log.info("FIND ALL: FOUND {} GROUPS", groups.getTotalElements());
            return new PageImpl<>(groups.stream().map(GroupResponseVO::new).toList(), pageable, groups.getTotalElements());
        } else {
            log.error("FIND ALL: GROUPS NOT FOUND");
            throw new EntityNotFoundException("Groups not found");
        }
    }

    public GroupResponseVO findById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: GROUP ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The group requested was not found");
        });
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", group);
        return new GroupResponseVO(group);
    }

    //    TODO(ver como passar permissions diretamente dentro do update)
    @Transactional
    public GroupResponseVO update(Long id, GroupRequestVO groupRequestVO) {
        Group group = groupRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: GROUP ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The group requested was not found");
        });
        BeanUtils.copyProperties(groupRequestVO, group, "id");
        group = groupRepository.save(group);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", group);
        return new GroupResponseVO(group);
    }

    // erro: failed to lazily initialize a collection: could not initialize proxy - no Session
    // ver: https://stackoverflow.com/questions/22821695/how-to-fix-hibernate-lazyinitializationexception-failed-to-lazily-initialize-a
    //  deve estar ligado ao relacionamento ManyToMany com permissions
    @Transactional
    public void delete(Long id) {
        try {
            groupRepository.deleteById(id);
            groupRepository.flush();
            log.info("DELETE: GROUP ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED GROUP ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("GROUP = " + id);
        }
    }

}

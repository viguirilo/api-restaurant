package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Group;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.GroupRepository;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final Logger logger = Logger.getLogger(GroupService.class.getName());

    //    TODO(ver como criar permissions diretamente dentro do save)
    @Transactional
    public GroupResponseVO save(GroupRequestVO groupRequestVO) {
        Group group = groupRepository.save(new Group(groupRequestVO));
        logger.info(group + " CREATED SUCCESSFULLY");
        return new GroupResponseVO(group);
    }

    public List<GroupResponseVO> findAll() {
        List<Group> groups = groupRepository.findAll();
        if (!groups.isEmpty()) {
            logger.info("FOUND " + groups.size() + " GROUPS");
            return groups.stream().map(GroupResponseVO::new).toList();
        } else {
            logger.warning("GROUPS NOT FOUND");
            throw new EntityNotFoundException("Groups not found");
        }
    }

    public GroupResponseVO findById(Long id) {
        Group group = groupRepository.findById(id).orElseThrow(() -> {
            logger.warning("GROUP ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The group requested was not found");
        });
        logger.info(group + " FOUND SUCCESSFULLY");
        return new GroupResponseVO(group);
    }

    //    TODO(ver como passar permissions diretamente dentro do update)
    @Transactional
    public GroupResponseVO update(Long id, GroupRequestVO groupRequestVO) {
        Group group = groupRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: GROUP " + id + " NOT FOUND");
            return new EntityNotFoundException("The group requested was not found");
        });
        BeanUtils.copyProperties(groupRequestVO, group, "id");
        group = groupRepository.save(group);
        logger.info(group + " UPDATED SUCCESSFULLY");
        return new GroupResponseVO(group);
    }

    // erro: failed to lazily initialize a collection: could not initialize proxy - no Session
    // ver: https://stackoverflow.com/questions/22821695/how-to-fix-hibernate-lazyinitializationexception-failed-to-lazily-initialize-a
    //  deve estar ligado ao relacionamento ManyToMany com permissions
    @Transactional
    public void delete(Long id) {
        try {
            groupRepository.delete(Objects.requireNonNull(groupRepository.findById(id).orElse(null)));
            logger.info("GROUP ID = " + id + " DELETED SUCCESSFULLY");
        } catch (NullPointerException ex) {
            logger.warning("CAN NOT DELETE: GROUP " + id + " NOT FOUND");
            throw new EntityNotFoundException("The group requested was not found");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (GROUP ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("GROUP = " + id);
        }
    }

}

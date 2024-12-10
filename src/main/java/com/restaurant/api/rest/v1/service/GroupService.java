package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.Group;
import com.restaurant.api.rest.v1.repository.GroupRepository;
import com.restaurant.api.rest.v1.vo.GroupRequestVO;
import com.restaurant.api.rest.v1.vo.GroupResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final Logger logger = Logger.getLogger(GroupService.class.getName());

    public GroupResponseVO save(GroupRequestVO groupRequestVO) {
        logger.info("Creating a new Group");
        return new GroupResponseVO(groupRepository.save(new Group(groupRequestVO)));
    }

    public List<GroupResponseVO> findAll() {
        logger.info("Returning Groups, if exists");
        return groupRepository.findAll().stream().map(GroupResponseVO::new).toList();
    }

    public GroupResponseVO findById(Long id) {
        logger.info("Returning Group id = " + id + ", if exists");
        Optional<Group> optionalGroup = groupRepository.findById(id);
        return optionalGroup.map(GroupResponseVO::new).orElse(null);
    }

    public Group update(Long id, GroupRequestVO groupRequestVO) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            BeanUtils.copyProperties(groupRequestVO, group, "id");
            logger.info("Updating Group id = " + id);
            return groupRepository.save(group);
        } else {
            logger.info("Couldn't update Group id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public Group delete(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            groupRepository.delete(group);
            logger.info("Updating Group id = " + id);
            return group;
        } else {
            logger.info("Couldn't delete Group id = " + id + " because it doesn't exists");
            return null;
        }
    }

}

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
            return null;
        }
    }

    public GroupResponseVO findById(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            logger.info(group + " FOUND SUCCESSFULLY");
            return new GroupResponseVO(group);
        } else {
            logger.warning("GROUP NOT FOUND");
            return null;
        }

    }

    public GroupResponseVO update(Long id, GroupRequestVO groupRequestVO) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            BeanUtils.copyProperties(groupRequestVO, group, "id");
            group = groupRepository.save(group);
            logger.info(group + " UPDATED SUCCESSFULLY");
            return new GroupResponseVO(group);
        } else {
            logger.warning("CAN NOT UPDATE: GROUP " + id + " NOT FOUND");
            return null;
        }
    }

    public GroupResponseVO delete(Long id) {
        Optional<Group> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            Group group = optionalGroup.get();
            groupRepository.delete(group);
            logger.info(group + " DELETED SUCCESSFULLY");
            return new GroupResponseVO(group);
        } else {
            logger.warning("CAN NOT DELETE: GROUP " + id + " NOT FOUND");
            return null;
        }
    }

}

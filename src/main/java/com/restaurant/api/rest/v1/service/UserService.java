package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.User;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.UserRepository;
import com.restaurant.api.rest.v1.vo.UserRequestVO;
import com.restaurant.api.rest.v1.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseVO save(UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        User user = userRepository.save(new User(userRequestVO));
        UserResponseVO userResponseVO = new UserResponseVO(user);
        log.info("SAVE: {} CREATED SUCCESSFULLY", userResponseVO);
        return userResponseVO;
    }

    private String getPasswordHash(String password) throws NoSuchAlgorithmException {
        Assert.notNull(password, "The password must not to be blank");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    public Page<UserResponseVO> findAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        if (!users.isEmpty()) {
            log.info("FIND ALL: FOUND {} USERS", users.getTotalElements());
            return new PageImpl<>(users.stream().map(UserResponseVO::new).toList(), pageable, users.getTotalElements());
        } else {
            log.error("FIND ALL: USERS NOT FOUND");
            throw new EntityNotFoundException("Users not found");
        }
    }

    public UserResponseVO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("FIND BY ID: USER ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The user requested was not found");
        });
        UserResponseVO userResponseVO = new UserResponseVO(user);
        log.info("FIND BY ID: {} FOUND SUCCESSFULLY", userResponseVO);
        return userResponseVO;
    }

    // TODO(Ver como a atualização do password pode prejudicar o Hash)
    @Transactional
    public UserResponseVO update(Long id, UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.error("UPDATE: USER ID = {} NOT FOUND", id);
            return new EntityNotFoundException("The user requested was not found");
        });
        if (userRequestVO.getPassword() != null && !userRequestVO.getPassword().isBlank())
            userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        BeanUtils.copyProperties(userRequestVO, user, "id", "password", "creationDate");
        user = userRepository.save(user);
        UserResponseVO userResponseVO = new UserResponseVO(user);
        log.info("UPDATE: {} UPDATED SUCCESSFULLY", userResponseVO);
        return userResponseVO;
    }

    @Transactional
    public void delete(Long id) {
        try {
            userRepository.deleteById(id);
            userRepository.flush();
            log.info("DELETE: USER ID = {} DELETED SUCCESSFULLY", id);
        } catch (DataIntegrityViolationException ex) {
            log.error("DELETE: THE REQUESTED USER ID = {} IS BEING USED BY ONE OR MORE ENTITIES", id);
            throw new EntityInUseException("USER = " + id);
        }
    }

}

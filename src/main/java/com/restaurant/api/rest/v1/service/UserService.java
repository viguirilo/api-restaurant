package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.User;
import com.restaurant.api.rest.v1.exception.EntityInUseException;
import com.restaurant.api.rest.v1.exception.EntityNotFoundException;
import com.restaurant.api.rest.v1.repository.UserRepository;
import com.restaurant.api.rest.v1.vo.UserRequestVO;
import com.restaurant.api.rest.v1.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    public UserResponseVO save(UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        User user = userRepository.save(new User(userRequestVO));
        logger.info(user + " CREATED SUCCESSFULLY");
        return new UserResponseVO(user);
    }

    private String getPasswordHash(String password) throws NoSuchAlgorithmException {
        Assert.notNull(password, "The password must not to be blank");
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    public List<UserResponseVO> findAll() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            logger.info("FOUND " + users.size() + " USERS");
            return users.stream().map(UserResponseVO::new).toList();
        } else {
            logger.warning("USERS NOT FOUND");
            throw new EntityNotFoundException("Users not found");
        }
    }

    public UserResponseVO findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.warning("USER ID = " + id + " NOT FOUND");
            return new EntityNotFoundException("The user requested was not found");
        });
        logger.info(user + " FOUND SUCCESSFULLY");
        return new UserResponseVO(user);
    }

    // TODO(Ver como a atualização do password pode prejudicar o Hash)
    public UserResponseVO update(Long id, UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        User user = userRepository.findById(id).orElseThrow(() -> {
            logger.warning("CAN NOT UPDATE: USER " + id + " NOT FOUND");
            return new EntityNotFoundException("The user requested was not found");
        });
        if (userRequestVO.getPassword() != null && !userRequestVO.getPassword().isBlank())
            userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        BeanUtils.copyProperties(userRequestVO, user, "id", "creationDate");
        user = userRepository.save(user);
        logger.info(user + " UPDATED SUCCESSFULLY");
        return new UserResponseVO(user);
    }

    public void delete(Long id) {
        try {
            userRepository.delete(Objects.requireNonNull(userRepository.findById(id).orElse(null)));
            logger.info("USER ID = " + id + " DELETED SUCCESSFULLY");
        } catch (NullPointerException ex) {
            logger.warning("CAN NOT DELETE: USER " + id + " NOT FOUND");
            throw new EntityNotFoundException("The user requested was not found");
        } catch (DataIntegrityViolationException ex) {
            logger.warning("THE REQUESTED ENTITY (USER ID = " + id + ") IS BEING USED BY ONE OR MORE ENTITIES");
            throw new EntityInUseException("USER = " + id);
        }
    }

}

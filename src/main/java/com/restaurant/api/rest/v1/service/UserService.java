package com.restaurant.api.rest.v1.service;

import com.restaurant.api.rest.v1.entity.User;
import com.restaurant.api.rest.v1.repository.UserRepository;
import com.restaurant.api.rest.v1.vo.UserRequestVO;
import com.restaurant.api.rest.v1.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final Logger logger = Logger.getLogger(UserService.class.getName());

    public User save(UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        logger.info("Creating a new User");
        userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        return userRepository.save(new User(userRequestVO));
    }

    private String getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    public List<UserResponseVO> findAll() {
        logger.info("Returning User, if exists");
        return userRepository.findAll().stream().map(UserResponseVO::new).toList();
    }

    public UserResponseVO findById(Long id) {
        logger.info("Returning User id = " + id + ", if exists");
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserResponseVO::new).orElse(null);
    }

    // TODO(Ver como a atualização do password pode prejudicar o Hash)
    public User update(Long id, UserRequestVO userRequestVO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            BeanUtils.copyProperties(userRequestVO, user, "id", "creationDate");
            logger.info("Updating User id = " + id);
            return userRepository.save(user);
        } else {
            logger.info("Couldn't update User id = " + id + " because it doesn't exists");
            return null;
        }
    }

    public User delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            logger.info("Deleting User id = " + id);
            return user;
        } else {
            logger.info("Couldn't delete User id = " + id + " because it doesn't exists");
            return null;
        }
    }

}

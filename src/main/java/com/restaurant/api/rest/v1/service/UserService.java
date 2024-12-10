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

    public UserResponseVO save(UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        User user = userRepository.save(new User(userRequestVO));
        logger.info(user + " CREATED SUCCESSFULLY");
        return new UserResponseVO(user);
    }

    private String getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    public List<UserResponseVO> findAll() {
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            logger.info("FOUND " + users.size() + " CITIES");
            return users.stream().map(UserResponseVO::new).toList();
        } else {
            logger.warning("USERS NOT FOUND");
            return null;
        }
    }

    public UserResponseVO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            logger.info(user + " FOUND SUCCESSFULLY");
            return new UserResponseVO(user);
        } else {
            logger.warning("USER NOT FOUND");
            return null;
        }
    }

    // TODO(Ver como a atualização do password pode prejudicar o Hash)
    public UserResponseVO update(Long id, UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
            BeanUtils.copyProperties(userRequestVO, user, "id", "creationDate");
            user = userRepository.save(user);
            logger.info(user + " UPDATED SUCCESSFULLY");
            return new UserResponseVO(user);
        } else {
            logger.warning("CAN NOT UPDATE: USER " + id + " NOT FOUND");
            return null;
        }
    }

    public UserResponseVO delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            logger.info(user + " DELETED SUCCESSFULLY");
            return new UserResponseVO(user);
        } else {
            logger.warning("CAN NOT DELETE: USER " + id + " NOT FOUND");
            return null;
        }
    }

}

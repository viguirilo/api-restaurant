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

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        userRequestVO.setPassword(getPasswordHash(userRequestVO.getPassword()));
        return userRepository.save(new User(userRequestVO));
    }

    private String getPasswordHash(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(password.getBytes());
        return new String(messageDigest.digest());
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public UserResponseVO findById(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        return userOptional.map(UserResponseVO::new).orElse(null);
    }

    public User update(Long id, UserRequestVO userRequestVO) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            BeanUtils.copyProperties(userRequestVO, user, "id", "creationDate");
            return userRepository.save(user);
        } else return null;
    }

    public User delete(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.delete(user);
            return user;
        } else return null;
    }

}

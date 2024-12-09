package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.entity.User;
import com.restaurant.api.rest.v1.service.UserService;
import com.restaurant.api.rest.v1.vo.UserRequestVO;
import com.restaurant.api.rest.v1.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/rest/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // TODO(colocar este método para retornar um VO)
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<UserResponseVO> save(@RequestBody UserRequestVO userRequestVO) throws NoSuchAlgorithmException {
        return ResponseEntity.ok().body(new UserResponseVO(userService.save(userRequestVO)));
    }

    // TODO(colocar paginação neste endpoint)
    // TODO(colocar este método para retornar um VO)
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> findAll() {
        return ResponseEntity.ok().body(userService.findAll());
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseVO> findById(@PathVariable Long id) {
        UserResponseVO userResponseVO = userService.findById(id);
        if (userResponseVO == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(userResponseVO);
    }

    // TODO(colocar este método para retornar um VO)
    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody UserRequestVO userRequestVO) {
        User user = userService.update(id, userRequestVO);
        if (user == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().body(user);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User user = userService.delete(id);
        if (user == null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok().build();
    }

}

package com.restaurant.api.rest.v1.controller;

import com.restaurant.api.rest.v1.controller.openapi.UserControllerOpenApi;
import com.restaurant.api.rest.v1.service.UserService;
import com.restaurant.api.rest.v1.vo.UserRequestVO;
import com.restaurant.api.rest.v1.vo.UserResponseVO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/rest/v1/users")
@RequiredArgsConstructor
public class UserController implements UserControllerOpenApi {

    private final UserService userService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseVO> save(@RequestBody UserRequestVO userRequestVO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userRequestVO));
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<UserResponseVO>> findAll(Pageable pageable) {
        Page<UserResponseVO> userResponseVOS = userService.findAll(pageable);
        return ResponseEntity.ok().body(userResponseVOS);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseVO> findById(@PathVariable Long id) {
        UserResponseVO userResponseVO = userService.findById(id);
        return ResponseEntity.ok().body(userResponseVO);
    }

    // TODO(Aulas 4.33 e 4.34 ensinam como fazer o UPDATE parcial usando o PATCH)
    @PutMapping(
            value = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<UserResponseVO> update(@PathVariable Long id, @RequestBody UserRequestVO userRequestVO) {
        try {
            UserResponseVO userResponseVO = userService.update(id, userRequestVO);
            return ResponseEntity.ok().body(userResponseVO);
        } catch (NoSuchAlgorithmException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

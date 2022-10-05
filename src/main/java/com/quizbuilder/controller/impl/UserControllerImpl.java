package com.quizbuilder.controller.impl;

import com.quizbuilder.model.Role;
import com.quizbuilder.model.User;
import com.quizbuilder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserControllerImpl {

    private final UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @PostMapping("/user/addRole")
    public ResponseEntity<User> addRoleToUser(@RequestBody User user, @RequestBody Role role) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }
}

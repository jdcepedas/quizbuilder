package com.quizbuilder.service.impl;

import com.quizbuilder.enums.RoleEnum;
import com.quizbuilder.exceptions.RoleException;
import com.quizbuilder.exceptions.UserException;
import com.quizbuilder.model.Role;
import com.quizbuilder.model.User;
import com.quizbuilder.repository.RoleRepository;
import com.quizbuilder.repository.UserRepository;
import com.quizbuilder.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final RoleRepository roleRepository;

    @Override
    public User saveUser(User user) {
        log.info("Saving new user: {} to db", user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public void addRoleToUser(String email, RoleEnum roleName) throws UserException, RoleException {

        log.info("Adding role {} to user {}", roleName, email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with email: " + email));

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RoleException("Role not found with name: " + roleName.name()));
        user.getRoles().add(role);
    }

    @Override
    public User getUser(String email) throws UserException {
        log.info("Fetching user {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException("User not found with email: " + email));
        return user;
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }
}

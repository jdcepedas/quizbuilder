package com.quizbuilder.service;

import com.quizbuilder.enums.RoleEnum;
import com.quizbuilder.exceptions.RoleException;
import com.quizbuilder.exceptions.UserException;
import com.quizbuilder.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    void addRoleToUser(String email, RoleEnum roleName) throws UserException, RoleException;

    User getUser(String email) throws UserException;

    List<User> getUsers();
}

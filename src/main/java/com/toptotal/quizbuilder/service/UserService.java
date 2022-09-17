package com.toptotal.quizbuilder.service;

import com.toptotal.quizbuilder.enums.RoleEnum;
import com.toptotal.quizbuilder.exceptions.RoleException;
import com.toptotal.quizbuilder.exceptions.UserException;
import com.toptotal.quizbuilder.model.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    void addRoleToUser(String email, RoleEnum roleName) throws UserException, RoleException;

    User getUser(String email) throws UserException;

    List<User> getUsers();
}

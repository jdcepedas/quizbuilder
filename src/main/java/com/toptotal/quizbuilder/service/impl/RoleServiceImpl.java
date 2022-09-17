package com.toptotal.quizbuilder.service.impl;

import com.toptotal.quizbuilder.model.Role;
import com.toptotal.quizbuilder.repository.RoleRepository;
import com.toptotal.quizbuilder.service.RoleService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
@Data
public class RoleServiceImpl implements RoleService {

    @Autowired
    private final RoleRepository roleRepository;

    public Role saveRole(Role role) {
        log.info("Saving new role {} to db", role.getName());
        return roleRepository.save(role);
    }
}

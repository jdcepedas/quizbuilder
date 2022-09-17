package com.toptotal.quizbuilder.repository;

import com.toptotal.quizbuilder.enums.RoleEnum;
import com.toptotal.quizbuilder.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleEnum name);
}

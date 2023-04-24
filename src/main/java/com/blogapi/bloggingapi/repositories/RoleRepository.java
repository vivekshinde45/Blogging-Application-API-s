package com.blogapi.bloggingapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.bloggingapi.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

}

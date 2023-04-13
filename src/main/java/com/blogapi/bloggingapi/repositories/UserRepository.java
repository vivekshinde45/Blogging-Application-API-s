package com.blogapi.bloggingapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.bloggingapi.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}

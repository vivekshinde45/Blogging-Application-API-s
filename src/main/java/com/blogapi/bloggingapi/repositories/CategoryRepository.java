package com.blogapi.bloggingapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.bloggingapi.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}

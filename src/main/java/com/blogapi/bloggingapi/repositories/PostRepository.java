package com.blogapi.bloggingapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.bloggingapi.entities.Category;
import com.blogapi.bloggingapi.entities.Post;
import com.blogapi.bloggingapi.entities.User;

public interface PostRepository extends JpaRepository<Post, Integer> {
    List<Post> findAllByUser(User user);

    List<Post> findAllByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}

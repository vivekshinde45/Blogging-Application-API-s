package com.blogapi.bloggingapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.bloggingapi.payload.PostDTO;
import com.blogapi.bloggingapi.services.Implementation.PostService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService _postService;

    // create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO postDTO, @PathVariable("userId") Integer userId,
            @PathVariable("categoryId") Integer categoryId) {
        PostDTO createdUser = this._postService.create(postDTO, userId, categoryId);
        return new ResponseEntity<PostDTO>(createdUser, HttpStatus.CREATED);
    }

    // get all posts
    @GetMapping("/posts/")
    public ResponseEntity<List<PostDTO>> getAll() {
        List<PostDTO> posts = this._postService.getAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // get all posts
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getById(@PathVariable("postId") Integer postId) {
        PostDTO post = this._postService.getById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // get posts by user
    @GetMapping("/user/{userId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostByUser(@PathVariable("userId") Integer userId) {
        List<PostDTO> postsByUser = this._postService.getAllByUser(userId);
        return new ResponseEntity<List<PostDTO>>(postsByUser, HttpStatus.OK);
    }

    // get posts by category
    @GetMapping("/category/{categoryId}/posts")
    public ResponseEntity<List<PostDTO>> getAllPostByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<PostDTO> postsByUser = this._postService.getAllByCategories(categoryId);
        return new ResponseEntity<List<PostDTO>>(postsByUser, HttpStatus.OK);
    }
}

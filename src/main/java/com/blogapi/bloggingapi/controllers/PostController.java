package com.blogapi.bloggingapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.bloggingapi.config.AppConstants;
import com.blogapi.bloggingapi.payload.ApiResponseBody;
import com.blogapi.bloggingapi.payload.PostDTO;
import com.blogapi.bloggingapi.payload.PostResponse;
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

    // update
    @PutMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> update(@Valid @RequestBody PostDTO postDTO, @PathVariable("postId") Integer postId) {
        PostDTO updatedPost = this._postService.update(postDTO, postId);
        return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/posts/{postId}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable("postId") Integer postId) {
        this._postService.delete(postId);
        return new ResponseEntity<ApiResponseBody>(new ApiResponseBody("Post deleted successfully", true),
                HttpStatus.OK);
    }

    // get all posts
    @GetMapping("/posts/")
    public ResponseEntity<List<PostDTO>> getAll() {
        List<PostDTO> posts = this._postService.getAll();
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    // get single posts
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDTO> getById(@PathVariable("postId") Integer postId) {
        PostDTO post = this._postService.getById(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    // get posts as per pages
    @GetMapping("/posts")
    public ResponseEntity<PostResponse> getAllPostByPages(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir) {
        PostResponse allPosts = this._postService.getByPage(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allPosts, HttpStatus.OK);
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

    // search by title
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDTO>> searchByTitle(@PathVariable("keyword") String keyword) {
        List<PostDTO> posts = this._postService.search(keyword);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}

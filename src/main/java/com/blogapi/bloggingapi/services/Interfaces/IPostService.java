package com.blogapi.bloggingapi.services.Interfaces;

import java.util.List;

import com.blogapi.bloggingapi.payload.PostDTO;

public interface IPostService {
    // create
    PostDTO create(PostDTO postDTO, Integer userId, Integer categoryId);

    // update
    PostDTO update(PostDTO postDTO, Integer postId);

    // delete
    void delete(Integer postId);

    // get all
    List<PostDTO> getAll();

    // get by ID
    PostDTO getById(Integer postId);

    // get all by user
    List<PostDTO> getAllByUser(Integer userId);

    // get all by categories
    List<PostDTO> getAllByCategories(Integer categoryId);
}

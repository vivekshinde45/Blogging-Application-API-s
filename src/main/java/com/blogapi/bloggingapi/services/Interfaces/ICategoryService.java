package com.blogapi.bloggingapi.services.Interfaces;

import java.util.List;

import com.blogapi.bloggingapi.payload.CategoryDTO;

public interface ICategoryService {
    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO, Integer categoryId);

    void delete(Integer categoryId);

    List<CategoryDTO> getAll();

    CategoryDTO getById(Integer categoryId);
}

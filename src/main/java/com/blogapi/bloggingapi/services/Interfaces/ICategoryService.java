package com.blogapi.bloggingapi.services.Interfaces;

import java.util.List;

import com.blogapi.bloggingapi.payload.CategoryDTO;
import com.blogapi.bloggingapi.payload.CategoryResponse;

public interface ICategoryService {
    CategoryDTO create(CategoryDTO categoryDTO);

    CategoryDTO update(CategoryDTO categoryDTO, Integer categoryId);

    void delete(Integer categoryId);

    List<CategoryDTO> getAll();

    CategoryResponse getByPage(Integer pageNumber, Integer pageSize);

    CategoryDTO getById(Integer categoryId);
}

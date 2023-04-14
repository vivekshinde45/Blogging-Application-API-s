package com.blogapi.bloggingapi.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.Category;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.CategoryDTO;
import com.blogapi.bloggingapi.repositories.CategoryRepository;
import com.blogapi.bloggingapi.services.Interfaces.ICategoryService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository _categoryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public CategoryDTO create(CategoryDTO categoryDTO) {
        Category category = this.dtoToObj(categoryDTO);
        Category savedCategory = this._categoryRepository.save(category);
        return this.objToDto(savedCategory);
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO, Integer categoryId) {
        Category category = this._categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category",
                        "Id",
                        categoryId));
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        Category updatedCategory = this._categoryRepository.save(category);
        return this.objToDto(updatedCategory);
    }

    @Override
    public void delete(Integer categoryId) {
        Category category = this._categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category",
                        "Id",
                        categoryId));
        this._categoryRepository.delete(category);
    }

    @Override
    public List<CategoryDTO> getAll() {
        List<Category> categories = this._categoryRepository.findAll();

        // map
        List<CategoryDTO> categoryDTOs = categories.stream().map(
                category -> this.objToDto(category)).collect(Collectors.toList());
        return categoryDTOs;
    }

    @Override
    public CategoryDTO getById(Integer categoryId) {
        Category category = this._categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category",
                        "Id",
                        categoryId));
        return this.objToDto(category);
    }

    public Category dtoToObj(CategoryDTO categoryDTO) {
        return objectMapper.convertValue(categoryDTO, new TypeReference<Category>() {
        });
    }

    public CategoryDTO objToDto(Category category) {
        return objectMapper.convertValue(category, new TypeReference<CategoryDTO>() {
        });
    }

}

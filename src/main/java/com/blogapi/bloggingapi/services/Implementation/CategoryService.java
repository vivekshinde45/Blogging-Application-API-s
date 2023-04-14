package com.blogapi.bloggingapi.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.Category;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.CategoryDTO;
import com.blogapi.bloggingapi.payload.CategoryResponse;
import com.blogapi.bloggingapi.repositories.CategoryRepository;
import com.blogapi.bloggingapi.services.Interfaces.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository _categoryRepository;

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
    public CategoryResponse getByPage(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Category> postRecords = this._categoryRepository.findAll(pageable);
        List<Category> allPosts = postRecords.getContent();

        List<CategoryDTO> posts = allPosts.stream().map(
                category -> this.objToDto(category)).collect(Collectors.toList());

        CategoryResponse response = new CategoryResponse();
        response.setContent(posts);
        response.setPageNumber(postRecords.getNumber());
        response.setPageSize(postRecords.getSize());
        response.setTotalElements(postRecords.getTotalElements());
        response.setTotalPages(postRecords.getTotalPages());
        response.setLastPage(postRecords.isLast());
        return response;
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
        Category category = new Category();
        // category.setCategoryId(categoryDTO.getCategoryId());
        category.setCategoryName(categoryDTO.getCategoryName());
        category.setCategoryDescription(categoryDTO.getCategoryDescription());
        return category;
    }

    public CategoryDTO objToDto(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryId(category.getCategoryId());
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setCategoryDescription(category.getCategoryDescription());
        return categoryDTO;
    }

}

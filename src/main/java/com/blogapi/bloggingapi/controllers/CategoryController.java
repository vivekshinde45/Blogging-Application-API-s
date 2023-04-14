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
import org.springframework.web.bind.annotation.RestController;

import com.blogapi.bloggingapi.payload.ApiResponseBody;
import com.blogapi.bloggingapi.payload.CategoryDTO;
import com.blogapi.bloggingapi.services.Implementation.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService _categoryService;

    // POST
    @PostMapping("/")
    public ResponseEntity<CategoryDTO> create(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = this._categoryService.create(categoryDTO);
        return new ResponseEntity<CategoryDTO>(savedCategory, HttpStatus.CREATED);
    }

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id) {
        CategoryDTO savedCategory = this._categoryService.update(categoryDTO, id);
        return new ResponseEntity<CategoryDTO>(savedCategory, HttpStatus.CREATED);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable("id") Integer id) {
        this._categoryService.delete(id);
        ApiResponseBody responseBody = new ApiResponseBody("Category deleted successfully", true);
        return ResponseEntity.ok(responseBody);
    }

    // GETall
    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAll() {
        List<CategoryDTO> users = this._categoryService.getAll();
        return ResponseEntity.ok(users);
    }

    // GETbyID
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getById(@PathVariable("id") Integer id) {
        CategoryDTO user = this._categoryService.getById(id);
        if (user != null) {
            return ResponseEntity.ok(user);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

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
import com.blogapi.bloggingapi.payload.RoleDTO;
import com.blogapi.bloggingapi.services.Implementation.RoleService;

@RestController
@RequestMapping("/api/role")
public class RoleController {
    @Autowired
    private RoleService _roleService;

    // create
    @PostMapping("/")
    public ResponseEntity<RoleDTO> create(@RequestBody RoleDTO roleDTO) {
        RoleDTO role = this._roleService.create(roleDTO);
        return new ResponseEntity<>(role, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleDTO> update(@RequestBody RoleDTO roleDTO, @PathVariable Integer roleId) {
        RoleDTO role = this._roleService.update(roleDTO, roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{roleId}")
    public ResponseEntity<ApiResponseBody> delete(@PathVariable Integer roleId) {
        this._roleService.delete(roleId);
        return new ResponseEntity<>(
                new ApiResponseBody("Role is deleted successfully ", true), HttpStatus.OK);
    }

    // get all
    @GetMapping("/")
    public ResponseEntity<List<RoleDTO>> getAll() {
        List<RoleDTO> roles = this._roleService.getAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    // get By Id
    @GetMapping("/{roleId}")
    public ResponseEntity<RoleDTO> getById(@PathVariable Integer roleId) {
        RoleDTO role = this._roleService.getById(roleId);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
}

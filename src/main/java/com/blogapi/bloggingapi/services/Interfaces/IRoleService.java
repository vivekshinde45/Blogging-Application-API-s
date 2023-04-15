package com.blogapi.bloggingapi.services.Interfaces;

import java.util.List;

import com.blogapi.bloggingapi.payload.RoleDTO;

public interface IRoleService {
    // create
    RoleDTO create(RoleDTO roleDTO);

    // update
    RoleDTO update(RoleDTO roleDTO, Integer roleId);

    // delete
    void delete(Integer roleId);

    // getAll
    List<RoleDTO> getAll();

    // get By Id
    RoleDTO getById(Integer roleId);
}

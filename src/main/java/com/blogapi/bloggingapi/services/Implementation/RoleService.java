package com.blogapi.bloggingapi.services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapi.bloggingapi.entities.Role;
import com.blogapi.bloggingapi.exceptions.ResourceNotFoundException;
import com.blogapi.bloggingapi.payload.RoleDTO;
import com.blogapi.bloggingapi.repositories.RoleRepository;
import com.blogapi.bloggingapi.services.Interfaces.IRoleService;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private RoleRepository _roleRepository;

    @Override
    public RoleDTO create(RoleDTO roleDTO) {
        Role role = this.dtoToObj(roleDTO);
        Role createdRole = this._roleRepository.save(role);
        return this.objToDto(createdRole);
    }

    @Override
    public RoleDTO update(RoleDTO roleDTO, Integer roleId) {
        Role role = this._roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role",
                        " Id ",
                        roleId));
        role.setName(roleDTO.getName());
        Role updatedRole = this._roleRepository.save(role);
        return this.objToDto(updatedRole);
    }

    @Override
    public void delete(Integer roleId) {
        Role role = this._roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role",
                        " Id ",
                        roleId));
        this._roleRepository.delete(role);
    }

    @Override
    public List<RoleDTO> getAll() {
        List<Role> allRoles = this._roleRepository.findAll();
        List<RoleDTO> roles = allRoles.stream().map(
                (role) -> this.objToDto(role))
                .collect(Collectors.toList());
        return roles;
    }

    @Override
    public RoleDTO getById(Integer roleId) {
        Role role = this._roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Role",
                        " Id ",
                        roleId));
        return this.objToDto(role);
    }

    public Role dtoToObj(RoleDTO roleDTO) {
        Role role = new Role();
        role.setName(roleDTO.getName());
        return role;
    }

    public RoleDTO objToDto(Role role) {
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setId(role.getId());
        roleDTO.setName(role.getName());
        return roleDTO;
    }

}

package com.nusiss.dmss.service;

import com.nusiss.dmss.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    public List<Role> getAllRoles();

    public Optional<Role> getRoleById(Long id);

    public Role saveRole(Role role);

    public void deleteRole(Long id);
}

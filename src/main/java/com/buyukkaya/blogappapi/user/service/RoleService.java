package com.buyukkaya.blogappapi.user.service;

import com.buyukkaya.blogappapi.user.model.entity.Role;

public interface RoleService {

    void addNewRole(String name);

    Role getRoleByName(String name);


}

package com.buyukkaya.blogappapi.user.service.imp;

import com.buyukkaya.blogappapi.user.exception.RoleNotFoundException;
import com.buyukkaya.blogappapi.user.model.entity.Role;
import com.buyukkaya.blogappapi.user.repository.RoleRepository;
import com.buyukkaya.blogappapi.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImp implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public void addNewRole(String name) {
        roleRepository.save(new Role(name));
    }

    @Override
    public Role getRoleByName(String name) throws RoleNotFoundException {

        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Role with name " + name + " is not found!"));

    }


}

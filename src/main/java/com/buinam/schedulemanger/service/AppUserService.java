package com.buinam.schedulemanger.service;

import com.buinam.schedulemanger.dto.SimpleUserDTO;
import com.buinam.schedulemanger.model.AppUser;
import com.buinam.schedulemanger.model.Role;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    Role saveRole(Role role);
    void addRoleToUser(String userName, String roleName);
    AppUser getUser(String userName);
    List<AppUser> getUsers(String textSearch);

    List<SimpleUserDTO> searchUsers(String textSearch);
}


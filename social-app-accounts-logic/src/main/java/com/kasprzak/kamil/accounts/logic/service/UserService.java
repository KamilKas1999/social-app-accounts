package com.kasprzak.kamil.accounts.logic.service;



import com.kasprzak.kamil.accounts.domain.entity.Role;
import com.kasprzak.kamil.accounts.domain.entity.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String rolename);

    User getUserByName(String username);

    User getUserById(Long id);


    List<User> getUsers();

    Role getRole(String name);
}

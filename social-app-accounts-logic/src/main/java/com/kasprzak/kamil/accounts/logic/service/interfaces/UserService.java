package com.kasprzak.kamil.accounts.logic.service.interfaces;


import aaabbbccc.bbb.Role;
import aaabbbccc.bbb.User;

import java.util.List;

public interface UserService {

    User saveUser(User user);

    Role saveRole(Role role);

    void addRoleToUser(String username, String rolename);

    User getUser(String username);

    List<User> getUsers();

    Role getRole(String name);
}

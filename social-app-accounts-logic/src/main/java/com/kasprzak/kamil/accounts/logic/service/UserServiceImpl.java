package com.kasprzak.kamil.accounts.logic.service;



import  com.kasprzak.kamil.accounts.database.infrastructure.UserRepo;
import  com.kasprzak.kamil.accounts.database.infrastructure.RoleRepo;
import com.kasprzak.kamil.accounts.domain.entity.Role;
import com.kasprzak.kamil.accounts.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepo userRepo;

    private final RoleRepo roleRepo;

    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            log.error("no user");
            throw new UsernameNotFoundException("User don't exist");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }


    @Override
    public User saveUser(User user) {
        log.info("Save user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Save role");
        return roleRepo.save(role);
    }

    @Override
    public void addRoleToUser(String username, String rolename) {
        log.info("Add role to user");
        User user = userRepo.findByUsername(username);
        Role role = roleRepo.findByName(rolename);
        user.getRoles().add(role);
    }

    @Override
    public User getUserByName(String username) {
        log.info("Get user with name: {}", username);
        return userRepo.findByUsername(username);
    }

    @Override
    public User getUserById(Long id) {
        log.info("Get user with id: {}", id);
        return userRepo.getById(id);
    }

    @Override
    public List<User> getUsers() {
        log.info("Get users");
        return userRepo.findAll();
    }

    @Override
    public Role getRole(String name) {
        return roleRepo.findByName(name);
    }

}

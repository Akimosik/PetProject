package com.forum.example.ForumExample.Service.impl;

import com.forum.example.ForumExample.Dao.RoleDao;
import com.forum.example.ForumExample.Dao.UserDao;
import com.forum.example.ForumExample.Exception.IncorrectPasswordException;
import com.forum.example.ForumExample.Exception.UserNotFoundException;
import com.forum.example.ForumExample.Model.Role;
import com.forum.example.ForumExample.Model.User;
import com.forum.example.ForumExample.Service.RoleService;
import com.forum.example.ForumExample.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleService roleService;

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public void create(User user) {
        Set<Role> roles = new HashSet<>();
        Role role;
        if (user.getUsername().toUpperCase().contains("ADMIN")){
            role = roleService.findByName("ADMIN");
        }else {
            role = roleService.findByName("USER");
        }


        roles.add(role);
        user.setRoles(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDao.save(user);
    }

    @Override
    public void remove(int id) {
        remove(findById(id));
    }

    @Override
    public void remove(User user) {
        userDao.delete(user);
    }

    @Override
    public void remove(User user,
                       String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("user: " + user.getPassword() + " pass: " + passwordEncoder.encode(
                    password));
            throw new IncorrectPasswordException();
        }
        userDao.delete(user);
    }
}

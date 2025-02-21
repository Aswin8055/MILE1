package com.examly.springapp.service;

import java.util.List;

import com.examly.springapp.model.User;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User user);
    void deleteUser(Long id);
}
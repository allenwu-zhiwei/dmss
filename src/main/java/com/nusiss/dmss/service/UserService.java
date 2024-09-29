package com.nusiss.dmss.service;


import com.nusiss.dmss.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAllUsers();

    public Optional<User> getUserById(Integer id);

    public Optional<User> getUserByUsername(String username);

    public User saveUser(User user);

    public void deleteUser(Integer id);

    public List<User> findUserByUsernameAndPassword(String username, String password);

}

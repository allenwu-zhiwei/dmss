package com.nusiss.dmss.dao;


import com.nusiss.dmss.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);

    @Query(
            value = "SELECT * FROM users u WHERE u.username = ?1 and u.password = ?2",
            nativeQuery = true)
    List<User> findUserByUsernameAndPassword(String username, String password);
}
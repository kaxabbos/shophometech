package com.shophometech.repo;

import com.shophometech.model.User;
import com.shophometech.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAllByRole(Role role);
    User findByRole(Role role);
}

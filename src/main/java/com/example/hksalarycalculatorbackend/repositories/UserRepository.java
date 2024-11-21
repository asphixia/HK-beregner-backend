package com.example.hksalarycalculatorbackend.repositories;

import com.example.hksalarycalculatorbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

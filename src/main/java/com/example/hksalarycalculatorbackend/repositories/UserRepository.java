package com.example.hksalarycalculatorbackend.repositories;

import com.example.hksalarycalculatorbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    //User findUserByUUID(UUID id);
}

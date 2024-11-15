package com.example.hksalarycalculatorbackend.repositories;
import com.example.hksalarycalculatorbackend.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminActionRepository extends CrudRepository<User, Long> {
}
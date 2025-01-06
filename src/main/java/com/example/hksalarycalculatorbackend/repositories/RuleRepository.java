package com.example.hksalarycalculatorbackend.repositories;

import com.example.hksalarycalculatorbackend.model.Rule;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface RuleRepository extends CrudRepository<Rule, Long> {

}

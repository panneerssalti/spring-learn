package com.panneer.springlearn.batch.repository;

import com.panneer.springlearn.batch.entity.Coffee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface CoffeeRepository extends JpaRepository<Coffee, Serializable> {
}

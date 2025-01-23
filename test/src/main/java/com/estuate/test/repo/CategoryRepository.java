package com.estuate.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.estuate.test.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Character> {

}

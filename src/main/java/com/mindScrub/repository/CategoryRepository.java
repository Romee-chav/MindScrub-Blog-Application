package com.mindScrub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindScrub.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}

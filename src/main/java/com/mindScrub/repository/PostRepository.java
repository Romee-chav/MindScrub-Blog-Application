package com.mindScrub.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mindScrub.entities.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer>{

}

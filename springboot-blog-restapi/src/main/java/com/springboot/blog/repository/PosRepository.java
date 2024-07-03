package com.springboot.blog.repository;

import com.springboot.blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosRepository extends JpaRepository<Post, Long> {
}

package com.example.simpletestblog.repository;

import com.example.simpletestblog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {

}

package io.dowlathbasha.restjpamysql.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.dowlathbasha.restjpamysql.model.Post;

public interface PostRepository extends JpaRepository<Post,Integer>{
      
}

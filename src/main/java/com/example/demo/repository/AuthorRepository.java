package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
  @Query(value = "SELECT COUNT(*) FROM tb_author", nativeQuery = true)
  public long count();

  @Query(value = "SELECT id, name FROM tb_author", nativeQuery = true)
  public List<Author> get();
}

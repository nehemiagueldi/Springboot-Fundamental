package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Book;
import com.example.demo.model.dto.BookDTO;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
  @Query("SELECT b FROM Book b")
  public List<Book> getListBook();

  @Query("SELECT b FROM Book b WHERE b.id = :bookId")
  public Book getBookById(@Param(value = "bookId") Integer bookId);

  // @Query("SELECT new com.example.demo.model.dto.BookDTO(b.title, a.name) FROM Book b JOIN b.author a WHERE b.id = 1")
  // public BookDTO getUsingDTO();

  @Query("SELECT new com.example.demo.model.dto.BookDTO(b.id, b.title, b.year, b.price, b.count, p.name, a.name) FROM Book b JOIN b.author a JOIN b.publisher p ORDER BY b.id ASC")
  public List<BookDTO> getBookUsingDTO();

  @Query("DELETE FROM Book b WHERE b.id = :bookId")
  public Book deleteBookById(@Param(value = "bookId") Integer bookId);

  @Query("UPDATE Book b SET b.title = :title, b.year = :year, b.price = :price, b.count = :count, b.publisher = :publisher, b.author = :author WHERE b.id = :id")
  public BookDTO updateBookUsingDTO();

}

package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api") // routing (/api)
public class BookRestController {
  private BookRepository bookRepository;

  @Autowired
  public BookRestController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  @CrossOrigin
  @GetMapping("book")
  public ResponseEntity<Object> getBooks(){
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", bookRepository.findAll());
  }

  @CrossOrigin
  @GetMapping("book/{id}")
  public ResponseEntity<Object> getBookById(@PathVariable Integer id) {
    Book book = bookRepository.findById(id).orElse(null);
    if (book != null) {
      return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", book);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Tidak Ditemukan");
    }
  }
}

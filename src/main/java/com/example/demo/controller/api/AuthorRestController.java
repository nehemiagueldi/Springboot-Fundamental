package com.example.demo.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Author;
import com.example.demo.model.Role;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("api")
public class AuthorRestController {
  private AuthorRepository authorRepository;

  @Autowired
  public AuthorRestController(AuthorRepository authorRepository){
    this.authorRepository = authorRepository;
  }

  @GetMapping("author")
  public ResponseEntity<Object> getAuthors(){
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", authorRepository.findAll());
  }

  @PostMapping("author/add")
  public ResponseEntity<Object> addAuthor(@RequestBody Author author) {
    Author newAuthor = new Author();
    newAuthor.setName(author.getName());
    newAuthor.setEmail(author.getEmail());

    authorRepository.save(newAuthor);
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Disimpan", authorRepository.save(newAuthor));
  }

  @DeleteMapping("author/{id}")
  public ResponseEntity<Object> deleteAuthor(@PathVariable Integer id){
    Author author = authorRepository.findById(id).orElse(null);
    if (author != null) {
      authorRepository.deleteById(id);
      return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Dihapus", null);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Tidak Ditemukan");
    }
  }

  @GetMapping("author/{id}")
  public ResponseEntity<Object> getAuthorById(@PathVariable Integer id) {
    Author author = authorRepository.findById(id).orElse(null);
    if (author != null) {
      return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", author);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "Data Tidak Ditemukan");
    }
  }
}

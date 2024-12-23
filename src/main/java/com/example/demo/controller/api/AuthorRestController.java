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
import com.example.demo.repository.AuthorRepository;
import com.example.demo.utils.CustomResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    newAuthor.setIsActive(author.getIsActive() != null ? author.getIsActive() : false);
    // newAuthor.setIsActive(author.getIsActive());

    authorRepository.save(newAuthor);
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Disimpan", authorRepository.save(newAuthor));
  }

  @PutMapping("author/edit/{id}")
  public  ResponseEntity<Object> editAuthor(@RequestBody Author author) {
    Author existAuthor = authorRepository.findById(author.getId()).orElse(null);

    existAuthor.setId(author.getId());
    existAuthor.setName(author.getName());
    existAuthor.setEmail(author.getEmail());
    authorRepository.save(existAuthor);

    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Diubah", authorRepository.save(existAuthor));
  }

  @DeleteMapping("author/delete/{id}")
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

  @PutMapping("author/status/{id}")
  public ResponseEntity<Object> updateActiveStatus(@PathVariable Integer id, @RequestBody Author author) {
    Boolean isActive = author.getIsActive();
    Author authorStatus = authorRepository.findById(id).orElse(null);

    authorStatus.setIsActive(isActive);
    authorRepository.save(authorStatus);

    return CustomResponse.generate(HttpStatus.OK, "Status berhasil diubah");
  }
}

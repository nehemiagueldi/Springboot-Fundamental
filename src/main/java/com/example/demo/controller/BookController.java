package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.model.Book;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.PublisherRepository;

@Controller
@RequestMapping("book") // routing (/book)
public class BookController {
  private BookRepository bookRepository;
  private PublisherRepository publisherRepository;
  private AuthorRepository authorRepository;

  @Autowired
  public BookController(BookRepository bookRepository, PublisherRepository publisherRepository, AuthorRepository authorRepository) {
    this.bookRepository = bookRepository;
    this.publisherRepository = publisherRepository;
    this.authorRepository = authorRepository;
  }

  // localhost:8080/book -> index
  @GetMapping
  public String index(Model model){
    model.addAttribute("books", bookRepository.getBookUsingDTO());
    UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    model.addAttribute("user", user);
    return "book/index"; // path atau lokasi yang menampilkan html
  }

  // localhost:8080/book/form -> create & update
  @GetMapping(value = {"form", "form/{id}"})
  public String form(@PathVariable(required = false) Integer id, Model model){
    if (id != null) {
      model.addAttribute("book", bookRepository.getBookById(id)); // update
    } else {
      model.addAttribute("book", new Book()); // create
    }
    model.addAttribute("publishers", publisherRepository.findAll());
    model.addAttribute("authors", authorRepository.findAll());
    return "book/form";
  }

  // localhost:8080/book/save -> save data yang dikirim dari html
  @PostMapping("save")
  public String save(Book book){
    bookRepository.save(book);
    return "redirect:/book";
  }

  // localhost:8080/book/{id} -> delete
  @PostMapping("delete/{id}")
  public String delete(@PathVariable Integer id){
    bookRepository.deleteBookById(id);
    return "redirect:/book";
  }
}
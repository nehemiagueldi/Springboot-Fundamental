package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Author;
import com.example.demo.repository.AuthorRepository;

@Controller
@RequestMapping("author") // routing (/author)
public class AuthorController {
  private AuthorRepository authorRepository;
  
  @Autowired
  public AuthorController (AuthorRepository authorRepository){
    this.authorRepository = authorRepository;
  }

  // endpoint karena menggunakan anotasi getmapping
  // localhost:8080/author -> index
  @GetMapping
  public String index(Model model){
    model.addAttribute("authors", authorRepository.findAll());
    return "author/index"; // path atau lokasi yang menampilkan html
  }

  // localhost:8080/author/form -> create & update
  @PostMapping(value = {"form", "form/{id}"})
  public String form(@PathVariable(value = "id", required = false) Integer id, Model model){
    if (id != null) {
      model.addAttribute("author", authorRepository.findById(id).get()); // update
    } else {
      model.addAttribute("author", new Author()); // create
    }
    return "author/form";
  }

  // localhost:8080/author/save -> save data yang dikirim dari html
  @PostMapping("save")
  public String save(Author author){
    authorRepository.save(author);
    return "redirect:/author";
  }

  // localhost:8080/author/edit -> update
  @GetMapping("edit/{id}")
  public String edit(@PathVariable(value = "id") Integer id, Model model){
    model.addAttribute("author", authorRepository.findById(id).get());
    return "author/edit";
  }

  // localhost:8080/author/delete -> delete
  @PostMapping("delete")
  public String delete(@RequestParam(value = "id") Integer id, Model model){
    authorRepository.deleteById(id);
    return "redirect:/author";
  }

  // localhost:8080/author/{id} -> delete
  @PostMapping("delete/{id}")
  public String delete(@PathVariable(value = "id") Integer id){
    authorRepository.deleteById(id);
    return "redirect:/author";
  }
}

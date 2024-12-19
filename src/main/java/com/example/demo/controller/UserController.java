package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired private UserRepository userRepo;

  @GetMapping("/info")
  public User getUserDetails(){
    // Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    // String email;
    // if (principal instanceof org.springframework.security.core.userdetails.User) {
    //     email = ((org.springframework.security.core.userdetails.User) principal).getUsername();
    // } else if (principal instanceof String) {
    //     email = (String) principal; // Jika menggunakan String sebagai principal
    // } else {
    //     throw new IllegalStateException("Unexpected principal type: " + principal.getClass().getName());
    // }

    // return userRepo.findByEmail(email);
    String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    // return CustomResponse.generate(HttpStatus.OK, "Login Berhasil", email);

    return userRepo.findByEmail(email);
  }
}

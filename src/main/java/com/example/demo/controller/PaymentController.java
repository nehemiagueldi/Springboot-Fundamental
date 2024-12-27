package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.TransactionDTO;
import com.example.demo.repository.TransactionRepository;

@Controller
@RequestMapping("payment") // routing (/payment)
public class PaymentController {
  private TransactionRepository transactionRepository;

  @Autowired
  public PaymentController(TransactionRepository transactionRepository){
    this.transactionRepository = transactionRepository;
  }

  @GetMapping
  public String index(Model model){
    model.addAttribute("payments", transactionRepository.findAll());
    return "payment/transaction"; // path atau lokasi yang menampilkan html
  }
}

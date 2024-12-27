package com.example.demo.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.TransactionDTO;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.TransactionRepository;
import com.example.demo.utils.CustomResponse;

@RestController
@RequestMapping("api") // routing (/api)
public class PaymentRestController {
  private TransactionRepository transactionRepository;

  @Autowired
  public PaymentRestController(TransactionRepository transactionRepository){
    this.transactionRepository = transactionRepository;
  }

  @GetMapping("list/transaction")
  public ResponseEntity<Object> getMethodName(@RequestParam(required = false) String search) {
    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Didapatkan", transactionRepository.findAll());
  }
  
  @GetMapping("transaction")
  public Map<String, Object> getTransaction(
    @RequestParam int draw,
    @RequestParam int start,
    @RequestParam int length,
    @RequestParam(value = "search") String search
  ) {
    Integer page = start / length;
    
    Pageable pageable = PageRequest.of(page, length);
    Page<Object[]> transactions = transactionRepository.searchTransactions(search, pageable);

    long totalRecords = getTotalRecords();
    long filteredRecords = getFilteredRecordCount(search);

    Map<String, Object> response = new HashMap<>();
    response.put("draw", draw);
    response.put("recordsTotal", totalRecords);
    response.put("recordsFiltered", filteredRecords);
    response.put("data", transactions.getContent());
    
    return response;
  }

  private long getTotalRecords() {
    return transactionRepository.count();
  }

  private long getFilteredRecordCount(String search) {
    return transactionRepository.countFiltered(search);
  }
}
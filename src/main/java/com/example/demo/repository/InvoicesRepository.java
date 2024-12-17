package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Invoices;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoices, String> {
  
}

package com.example.demo.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_invoices") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Invoices {
  @Id // PK
  private String id;
  private Integer totalPrice;
  private LocalDateTime createdAt;
  private LocalDateTime expiredDate;
  @OneToMany(mappedBy = "invoices") //invoices dari penamaan diclass Invoices Details
  private List<InvoicesDetails> invoicesDetails;
}

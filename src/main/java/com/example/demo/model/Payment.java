package com.example.demo.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_payment") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Payment {
  @Id // PK
  private String id;
  private LocalDateTime transactionDate;
  @ManyToOne
  @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
  private PaymentMethod paymentMethod;
  @ManyToOne
  @JoinColumn(name = "payment_status_id", referencedColumnName = "id")
  private PaymentStatus paymentStatus;
  // private Invoices invoices;
  @ManyToOne
  @JoinColumn(name = "bank_details_id", referencedColumnName = "id")
  private BankDetails bankDetails;
}

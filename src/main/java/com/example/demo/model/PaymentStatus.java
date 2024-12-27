package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_payment_status") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class PaymentStatus {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private int id;
  private String name;
  @OneToMany(mappedBy = "paymentStatus")
  @JsonIgnore
  private List<Payment> payments;
}

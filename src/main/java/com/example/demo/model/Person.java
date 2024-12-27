package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_person") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Person {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private Integer id;
  private String name;
  private String nickname;
  private String email;
  private Integer phone;
  private String address;
  @OneToMany(mappedBy = "person") //person dari penamaan diclass Invoices Details
  @JsonIgnore
  private List<InvoicesDetails> invoicesDetails;
  @OneToOne(mappedBy = "person") //person dari penamaan diclass Invoices Details
  @JsonIgnore
  private User user;
}

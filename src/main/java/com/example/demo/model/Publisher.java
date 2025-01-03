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

// import com.fasterxml.jackson.annotation.JsonIgnore;
@Entity(name = "tb_publisher") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Publisher {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private Integer id; 
  private String name;
  private String email;
  private Integer phone;
  private String address;
  @OneToMany(mappedBy = "publisher") //publisher dari penamaan diclass Book
  @JsonIgnore // buat json ignore
  private List<Book> books;
}

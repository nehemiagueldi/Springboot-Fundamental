package com.example.demo.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity //define sebuah table didatabase
@Table(name = "tb_book")
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class Book {
  @Id // PK
  @GeneratedValue(strategy = GenerationType.IDENTITY) // AI
  private Integer id;
  private String title;
  private Integer year;
  private Integer price;
  private Integer count;
  @ManyToOne
  @JoinColumn(name = "publisher_id", referencedColumnName = "id")
  private Publisher publisher;
  @ManyToOne
  @JoinColumn(name = "author_id", referencedColumnName = "id")
  private Author author;
  @OneToMany(mappedBy = "book") //book dari penamaan diclass Invoices Details
  List<InvoicesDetails> invoicesDetails;
}

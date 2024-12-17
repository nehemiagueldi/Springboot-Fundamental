package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "tb_invoices_details") //define sebuah table didatabase
@Data //anotasi data, gantinya setter getter dari lombok
@AllArgsConstructor //constructor dengan parameter semua properti
@NoArgsConstructor //constructor tidak menggunakan parameter
public class InvoicesDetails {
  @Id // PK
  private String id;
  private Integer quantity;
  @ManyToOne
  @JoinColumn(name = "person_id", referencedColumnName = "id")
  private Person person;
  @ManyToOne
  @JoinColumn(name = "book_id", referencedColumnName = "id")
  private Book book;
  @ManyToOne
  @JoinColumn(name = "invoices_id", referencedColumnName = "id")
  private Invoices invoices;
}

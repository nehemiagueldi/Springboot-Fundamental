package com.example.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO { // Untuk menampung kolom yang diSELECT
  private Integer id;
  private String title;
  private Integer year;
  private Integer price;
  private Integer count;
  private String publisherName;
  private String authorName;
}

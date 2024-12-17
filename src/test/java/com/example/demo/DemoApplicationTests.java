package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.repository.AuthorRepository;
import com.example.demo.repository.BookRepository;
// import com.example.demo.repository.PublisherRepository;
import com.example.demo.repository.UserRepository;

@SpringBootTest
class DemoApplicationTests {

	// Cara 1
	@Autowired // depedency injection
	private AuthorRepository authorRepository;
	// @Autowired
	// private PublisherRepository publisherRepository;
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private UserRepository userRepository;
	// Cara 2 
	// private AuthorRepository authorRepository;

	// @Autowired
	// public DemoApplicationTests(AuthorRepository authorRepository) {
	// 	this.authorRepository = authorRepository;
	// }

	@Test
	void contextLoads(){
		// Arrange
		long expectTotalAuthor = 9;
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		// Integer expectTotalPublisher = 5;
		// Integer expectTotalBook = 10;
		// Integer expectStockBook = 47;
		// String expectTitleBook = "Negeri 5 Menara";
		// Integer expectYearBook = 1967;

		// Act
		long actualTotalAuthor = authorRepository.count();
		// Integer actualTotalBook = bookRepository.findAll().size();
		// Integer actualStockBook = bookRepository.findById(1).get().getCount();
		// String actualTitleBook = bookRepository.findById(1).get().getTitle();
		// Integer actualYearBook = bookRepository.findById(1).get().getYear();

		// Assert
		// assertEquals("toor", userRepository.getUsers("betty@gmail.com").getPassword());
		// assertTrue(bCryptPasswordEncoder.matches("toor", userRepository.getUsers("betty@gmail.com").getPassword()));
		assertTrue(bCryptPasswordEncoder.matches("toor123", userRepository.getUsers("rama@gmail.com").getPassword()));
		// assertEquals(expectTotalAuthor, actualTotalAuthor);
		// assertEquals("Negeri 5 Menara", bookRepository.get(1).getTitle());
		// assertEquals("januar123", bookRepository.getUsingDTO().getName());
		// assertEquals(expectTotalBook, actualTotalBook);
		// assertEquals(expectStockBook, actualStockBook);
		// assertEquals(expectTitleBook, actualTitleBook);
		// assertEquals(expectYearBook, actualYearBook);
	}

}

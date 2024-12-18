package com.example.demo.controller.api;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Person;
import com.example.demo.model.ResetPasswordToken;
import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.ForgotPasswordDTO;
import com.example.demo.model.dto.LoginDTO;
import com.example.demo.model.dto.PersonDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ResetPasswordTokenRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.utils.CustomResponse;
import com.example.demo.utils.EmailService;

@RestController
@RequestMapping("api")
public class UserManagementRestController {
  private UserRepository userRepository;
  private PersonRepository personRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private EmailService emailService;
  private ResetPasswordTokenRepository resetPasswordTokenRepository;

  @Autowired
  public UserManagementRestController(UserRepository userRepository, PasswordEncoder passwordEncoder, PersonRepository personRepository, RoleRepository roleRepository, EmailService emailService, ResetPasswordTokenRepository resetPasswordTokenRepository){
    this.userRepository = userRepository;
    this.personRepository = personRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
  }

  @PostMapping("user/login")
  public ResponseEntity<Object> login(@RequestBody LoginDTO loginDTO){
    UserDTO userDTO = userRepository.getUsers(loginDTO.getEmail());
    if (userDTO != null && passwordEncoder.matches(loginDTO.getPassword(), userDTO.getPassword())) {
      try {
        org.springframework.security.core.userdetails.User userSecurity = new org.springframework.security.core.userdetails.User(
          userDTO.getId().toString(),
          "",
          getAuthorities(userDTO.getRole())
        );
        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(
          userDTO,
          "",
          userSecurity.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return CustomResponse.generate(HttpStatus.OK, "Login Berhasil", userDTO);
    } else {
      return CustomResponse.generate(HttpStatus.OK, "User tidak ditemukan, silahkan melakukan register");
    }
  }

  private static Collection<? extends GrantedAuthority> getAuthorities(String role) {
    final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    return authorities;
  }

  @PostMapping("user/register")
  public ResponseEntity<Object> register(@RequestBody PersonDTO personDTO){
    Person person = new Person();
    person.setName(personDTO.getName());
    person.setNickname(personDTO.getNickname());
    person.setAddress(personDTO.getAddress());
    person.setPhone(personDTO.getPhone());
    person.setEmail(personDTO.getEmail());

    person = personRepository.save(person);
    Integer personId = person.getId();

    com.example.demo.model.User user = new com.example.demo.model.User();
    user.setId(personId);
    user.setRole(roleRepository.getRoleWithMinLevel());
    user.setPassword(passwordEncoder.encode(personDTO.getPassword()));

    userRepository.save(user);

    return CustomResponse.generate(HttpStatus.OK, "Data Berhasil Disimpan", userRepository.save(user));
  }

  @PostMapping("user/changepassword")
  public ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO dataUpdated) {
    if (dataUpdated.getPassword() == null || dataUpdated.getPassword().isEmpty()) {
      return CustomResponse.generate(HttpStatus.BAD_REQUEST, "Password tidak boleh kosong");
    } else {
      com.example.demo.model.User userFound = userRepository.findById(dataUpdated.getId()).get();
      userFound.setPassword(passwordEncoder.encode(dataUpdated.getPassword()));
      userRepository.save(userFound);
      return CustomResponse.generate(HttpStatus.OK, "Password berhasil diganti", userFound);
    }
  }

  @PostMapping("user/forgotpassword")
  public ResponseEntity<Object> forgotPassword(@RequestBody ForgotPasswordDTO forgotPasswordDTO){
    com.example.demo.model.User user = userRepository.findByEmail(forgotPasswordDTO.getEmail());
    ResetPasswordToken existingToken = resetPasswordTokenRepository.findByUser(user);
    if (existingToken != null) {
      resetPasswordTokenRepository.delete(existingToken);
    }

    // Generate Reset Token
    String resetToken = UUID.randomUUID().toString();
    ResetPasswordToken token = new ResetPasswordToken();
    token.setToken(resetToken);
    token.setExpiredDate(LocalDateTime.now().plusHours(1)); // Token valid untuk 1 jam
    token.setUser(user);

    resetPasswordTokenRepository.save(token);

    // Email Confirmation
    String email = user.getPerson().getEmail();
    String resetLink = "http://localhost:8080/usermanagement/resetpassword?token=" + resetToken;
    String subject = "Password Reset Request " + LocalDate.now();
    String body = "Hi " + user.getPerson().getName() + ",\n\n"
                + "We received a request to reset your password. Please use the following link to set a new password:\n\n"
                + resetLink + "\n\n"
                + "This link will expire in 1 hour.\n"
                + "If you did not request this, please ignore this email or contact support.\n\n"
                + "Best regards,\nThe Team";

    emailService.sendEmail(email, subject, body);
    return CustomResponse.generate(HttpStatus.OK, "Request reset password telah dikirim melalui email");
  }
}

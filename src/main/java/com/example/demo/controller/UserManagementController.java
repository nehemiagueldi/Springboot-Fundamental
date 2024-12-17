package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Person;
import com.example.demo.model.ResetPasswordToken;
import com.example.demo.model.dto.ChangePasswordDTO;
import com.example.demo.model.dto.ForgotPasswordDTO;
import com.example.demo.model.dto.LoginDTO;
import com.example.demo.model.dto.PersonDTO;
import com.example.demo.model.dto.ResetPasswordDTO;
import com.example.demo.model.dto.UserDTO;
import com.example.demo.repository.PersonRepository;
import com.example.demo.repository.ResetPasswordTokenRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.EmailService;

@Controller
@RequestMapping("usermanagement") // routing (/usermanagement)
public class UserManagementController {
  private UserRepository userRepository;
  private PersonRepository personRepository;
  private RoleRepository roleRepository;
  private PasswordEncoder passwordEncoder;
  private EmailService emailService;
  private ResetPasswordTokenRepository resetPasswordTokenRepository;

  @Autowired
  public UserManagementController(UserRepository userRepository, PasswordEncoder passwordEncoder, PersonRepository personRepository, RoleRepository roleRepository, EmailService emailService, ResetPasswordTokenRepository resetPasswordTokenRepository){
    this.userRepository = userRepository;
    this.personRepository = personRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.emailService = emailService;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
  }

  // localhost:8080/login -> index
  @GetMapping("login")
  public String index(Model model){
    model.addAttribute("login", new LoginDTO());
    return "user/login"; // path atau lokasi yang menampilkan html
  }

  // localhost:8080/forgotpassword -> index
  @GetMapping("forgotpassword")
  public String forgotPassword(Model model){
    model.addAttribute("forgotPassword", new ForgotPasswordDTO());
    return "user/forgotpassword"; // path atau lokasi yang menampilkan html
  }

  @PostMapping("requestreset")
  public String requestReset(ForgotPasswordDTO forgotPasswordDTO, Model model){
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
    return "redirect:/usermanagement/forgotpassword";
  }

  @GetMapping("resetpassword")
  public String resetPassword(@RequestParam String token, Model model){
    ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByToken(token);

    ResetPasswordDTO resetPasswordDTO = new ResetPasswordDTO();
    resetPasswordDTO.setEmail(resetPasswordToken.getUser().getPerson().getEmail());

    model.addAttribute("reset", resetPasswordDTO);
    return "user/resetpassword"; // path atau lokasi yang menampilkan html
  }

  @PostMapping("reset")
  public String reset(ResetPasswordDTO resetPasswordDTO, Model model){
    com.example.demo.model.User user = userRepository.findByEmail(resetPasswordDTO.getEmail());
    ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByUser(user);

    String encodedPassword = passwordEncoder.encode(resetPasswordDTO.getPassword());
    user.setPassword(encodedPassword);

    userRepository.save(user);

    resetPasswordTokenRepository.delete(resetPasswordToken);

    String email = user.getPerson().getEmail();
    String subject = "Password Successfully Reset " + LocalDate.now();
    String body = "Hi " + user.getPerson().getName() + ",\n\n"
                + "Your password has been successfully changed.\n\n"
                + "For your security, here is your new password: " + resetPasswordDTO.getPassword() + "\n\n"
                + "If you did not perform this action, please contact our support immediately.\n\n"
                + "Best regards,\nThe Team";
    emailService.sendEmail(email, subject, body);

    return "redirect:/usermanagement/login";
  }

  // localhost:8080/changepassword -> index
  @GetMapping("changepassword/{id}")
  public String changePassword(@PathVariable Integer id, Model model){
    model.addAttribute("dataLogin", userRepository.getUsersById(id));
    return "user/changepassword"; // path atau lokasi yang menampilkan html
  }

  @PostMapping("update")
  public String update(ChangePasswordDTO dataUpdated){
    if (dataUpdated.getPassword() == null || dataUpdated.getPassword().isEmpty()) {
      return "redirect:/usermanagement/changepassword/" + dataUpdated.getId();
    } else {
      String encodedPassword = passwordEncoder.encode(dataUpdated.getPassword());

      com.example.demo.model.User user = userRepository.findById(dataUpdated.getId()).get();

      user.setPassword(encodedPassword);
      userRepository.save(user);

      // Email Confirmation
      String email = dataUpdated.getEmail();
      String subject = "Password Successfully Changed " + LocalDate.now();
      String body = "Hi " + user.getPerson().getName() + ",\n\n"
                    + "Your password has been successfully changed.\n"
                    + "For your security, here is your new password: " + dataUpdated.getPassword() + "\n\n"
                    + "If you did not perform this action, please contact our support immediately.\n\n"
                    + "Best regards,\nThe Team";
      emailService.sendEmail(email, subject, body);
      return "redirect:/book";
    }
  }

  // localhost:8080/register -> index
  @GetMapping("register")
  public String register(Model model) {
      model.addAttribute("newUser", new PersonDTO());
      return "user/register";
  }

  @PostMapping("save")
  public String save(PersonDTO personDTO){
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
    // user.setRole(roleRepository.getRoleById(2));
    user.setPassword(passwordEncoder.encode(personDTO.getPassword()));
    
    userRepository.save(user);

    // Email Confirmation
    String email = personDTO.getEmail();
    String subject = "Welcome to Our Platform! " + LocalDate.now();
    String body = "Hi " + personDTO.getName() + ",\n\nThank you for registering to our platform. "
                + "We are excited to have you on board!\n\nBest regards,\nThe Team";
    emailService.sendEmail(email, subject, body);
    
    return "redirect:/usermanagement/login";
  }

  @PostMapping("authenticate")
  public String authenticate(LoginDTO loginDTO){
    UserDTO userDTO = userRepository.getUsers(loginDTO.getEmail());
    // model.addAttribute("user", userRepository.getUsers(loginDTO.getEmail()));
    if (userDTO != null && passwordEncoder.matches(loginDTO.getPassword(), userDTO.getPassword())) {
      try {
        User userSecurity = new User(
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
    }
    return "redirect:/book";
  }

  private static Collection<? extends GrantedAuthority> getAuthorities(String role) {
    final List<SimpleGrantedAuthority> authorities = new LinkedList<>();
    authorities.add(new SimpleGrantedAuthority(role));
    return authorities;
  }
}

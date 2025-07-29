package tn.enis.controller;

import tn.enis.models.User;
import tn.enis.dto.LoginDTO;
import tn.enis.dto.RegisterDTO;
import tn.enis.jwt.JwtUtil;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import tn.enis.mapper.UserMapper;
import tn.enis.service.UserService;

import java.util.*;

@CrossOrigin(origins = "http://localhost:5173", methods = RequestMethod.POST)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public LoginDTO createUser(@RequestBody User user) {

        User savedUser = userService.createUser(user);
        return UserMapper.toDto(savedUser);
    }

    @GetMapping
    public List<User> getAllUsers() {

        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public Optional<User> getUserById(@PathVariable Long id) {

        return userService.getUserById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDTO registerDto) {
        String fullName = registerDto.getFullName();
        String email = registerDto.getEmail();
        String password = registerDto.getPassword();

        if (userService.userExists(fullName, email)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }

        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPassword(password);

        User savedUser = userService.save(user);
        String token = jwtUtil.generateToken(savedUser.getId(), fullName, password);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("accessToken", token));
    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginDTO loginDto) {
        String fullName = loginDto.getFullName();
        String password = loginDto.getPassword();

        Optional<User> userOpt = userService.login(fullName, password);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            String token = jwtUtil.generateToken(user.getId(), fullName, password);

            return ResponseEntity.ok(Map.of("accessToken", token));
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

}

package com.taskero.track.controller;

import com.taskero.track.dto.*;
import com.taskero.track.model.UserRole;
import com.taskero.track.model.User;
import com.taskero.track.repository.UserRepository;
import com.taskero.track.security.CustomUserDetailsService;
import com.taskero.track.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("Username is already taken");
        }

        if (request.getRoles() == null || request.getRoles().isEmpty()) {
            return ResponseEntity.badRequest().body("User roles cannot be empty");
        }

        // Validate roles and list roles
        
        List<UserRole> listRoles = new ArrayList<>();
        for (String roleStr : request.getRoles()) {
        	Optional<UserRole> role = UserRole.tryParse(roleStr);
        	if(!role.isPresent()) {
                return ResponseEntity.badRequest()
                    .body("Invalid role: " + roleStr + ". Allowed roles are ADMIN, MANAGER, CONTRIBUTOR.");
            }
        	
        	listRoles.add(role.get());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRoles(listRoles);

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }
}

package com.app.hospital.Controller;   // ✅ lowercase “controller”

import com.app.hospital.Config.JwtUtils;
import com.app.hospital.dto.AuthRequest;
import com.app.hospital.dto.AuthResponse;
import com.app.hospital.dto.RegisterRequest;
import com.app.hospital.Entity.User;
import com.app.hospital.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authMgr;
    private final JwtUtils jwt;
    private final UserRepository users;
    private final PasswordEncoder encoder;

    /** ---------- REGISTER ---------- */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest r) {
        if (users.existsByUsername(r.getUsername()))
            return ResponseEntity.status(HttpStatus.CONFLICT)
                                 .body("Username already exists");

        User u = new User();
        u.setUsername(r.getUsername());
        u.setPassword(encoder.encode(r.getPassword()));
        u.setEmail(r.getEmail());
        u.setRole(r.getRole() == null ? "ROLE_USER" : r.getRole());

        users.save(u);
        return ResponseEntity.ok("Registration successful");
    }

    /** ---------- LOGIN ---------- */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest r) {
        authMgr.authenticate(
            new UsernamePasswordAuthenticationToken(r.getUsername(), r.getPassword())
        );

        User user = users.findByUsername(r.getUsername()).orElseThrow();
        String token = jwt.generateToken(user.getUsername(), user.getRole());

        return ResponseEntity.ok(new AuthResponse(token));
    }
}

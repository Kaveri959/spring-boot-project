package com.app.hospital.Controller;

import com.app.hospital.Entity.User;
import com.app.hospital.Repository.UserRepository;
import com.app.hospital.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @GetMapping
    public List<User> getAll() {
        return userRepo.findAll();
    }

    @PostMapping
    public User create(@RequestBody User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        User existing = get(id);
        existing.setUsername(user.getUsername());
        existing.setEmail(user.getEmail());
        return userRepo.save(existing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        User existing = get(id);
        userRepo.delete(existing);
        return ResponseEntity.noContent().build();
    }
}

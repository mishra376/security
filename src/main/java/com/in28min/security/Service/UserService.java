package com.in28min.security.Service;

import com.in28min.security.Entity.User;
import com.in28min.security.Repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepo userRepo;
    private PasswordEncoder passwordEncoder;

    public ResponseEntity<?> save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity<?> findByUsername(String username) {
        return userRepo.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deleteByUsername(String username) {
        return userRepo.findByUsername(username)
                .map(user -> {
                    userRepo.delete(user);
                    return ResponseEntity.ok("Deleted");
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
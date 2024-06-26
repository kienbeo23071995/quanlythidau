package com.example.be.service.impl;

import com.example.be.entity.PasswordResetToken;
import com.example.be.entity.User;
import com.example.be.repository.PasswordResetTokenRepository;
import com.example.be.repository.UserRepository;
import com.example.be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final long EXPIRE_TOKEN=30;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public void createPasswordResetTokenForUser(Optional<User> user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(user.get(), token);
        passwordResetTokenRepository.save(myToken);
    }

    @Override
    public ResponseEntity<String> resetPass(String token, String password) {
        Optional<PasswordResetToken> userOptional= Optional.ofNullable(passwordResetTokenRepository.findByToken(token));

        if(userOptional.isEmpty()){
            return ResponseEntity.badRequest().body("Invalid token");
        }
        LocalDateTime tokenCreationDate = userOptional.get().getExpiryDate();

        if (isTokenExpired(tokenCreationDate)) {
            return ResponseEntity.badRequest().body("Token expired.");
        }
        User user = userOptional.get().getUser();
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        userOptional.get().setToken(null);
        userRepository.save(user);
        passwordResetTokenRepository.save(userOptional.get());
        return ResponseEntity.ok("Password reset successful");
    }

    private boolean isTokenExpired(final LocalDateTime tokenCreationDate) {

        LocalDateTime now = LocalDateTime.now();
        Duration diff = Duration.between(tokenCreationDate, now);

        return diff.toMinutes() >= EXPIRE_TOKEN;
    }

}

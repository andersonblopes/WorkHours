package com.lopes.WorkHoursApplication.service;

import com.lopes.WorkHoursApplication.domain.UserDetailsImpl;
import com.lopes.WorkHoursApplication.domain.entities.User;
import com.lopes.WorkHoursApplication.domain.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByUsername(username);
        if (user == null) {
            String message = "User  {} not found";
            log.error(message, username);
            throw new UsernameNotFoundException(message);
        }
        return new UserDetailsImpl(user.getId(), user.getUsername(), user.getPassword(), List.of());
    }

    public void createUser(String username, String password) {
        // Create a new User object
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        repository.save(user);
    }

    public User getAuthenticatedUser() {

        // Get the current authenticated user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Long userId = ((UserDetailsImpl) authentication.getPrincipal()).getId();

        Optional<User> user = repository.findById(userId);
        if (user.isPresent()) {
            return user.get();
        }

        throw new UsernameNotFoundException("User not authenticated");
    }

    public User findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with ID " + id + " not found"));
    }

    public List<User> findAll() {
        return repository.findAll();
    }
}

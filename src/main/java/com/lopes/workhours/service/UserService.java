package com.lopes.workhours.service;

import com.lopes.workhours.domain.UserDetailsImpl;
import com.lopes.workhours.domain.entities.User;
import com.lopes.workhours.domain.model.UserModel;
import com.lopes.workhours.domain.repository.UserRepository;
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
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = repository.findByLogin(login);
        if (user == null) {
            String message = "User  {} not found";
            log.error(message, login);
            throw new UsernameNotFoundException(message);
        }
        return new UserDetailsImpl(user.getId(), user.getLogin(), user.getPassword(), List.of());
    }

    public void createUser(UserModel userModel) {
        // Create a new User object
        User user = new User();
        user.setName(userModel.getName());
        user.setNickName(userModel.getNickName());
        user.setLogin(userModel.getLogin());
        user.setEmail(userModel.getEmail());
        user.setPassword(passwordEncoder.encode(userModel.getPassword()));
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

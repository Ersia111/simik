package com.simik.simik.service.impl;

import com.simik.simik.dto.LoginRequest;
import com.simik.simik.dto.LoginResponse;
import com.simik.simik.dto.RegisterRequest;
import com.simik.simik.entity.Role;
import com.simik.simik.entity.User;
import com.simik.simik.repository.RoleRepository;
import com.simik.simik.repository.UserRepository;
import com.simik.simik.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Ky email është përdorur më parë.");
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Roli nuk u gjet."));

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(role);

        userRepository.save(user);
    }

    @Override
    public LoginResponse loginUser(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email nuk ekziston."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Fjalëkalimi është i pasaktë.");
        }

        return new LoginResponse(
                "Login u krye me sukses.",
                user.getRole().getName()
        );
    }
}
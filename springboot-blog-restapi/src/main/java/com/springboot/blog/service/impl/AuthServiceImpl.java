package com.springboot.blog.service.impl;

import com.springboot.blog.entity.Role;
import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.RegisterDto;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String logIn(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(),
                loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User login successfully!!";
    }

    @Override
    public String register(RegisterDto registerDto) {
        //add check for username exist in db
        if (userRepository.existsByUsername(registerDto.getUsername())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Username already exist");
        }
        //add check for email exixt in db
        if (userRepository
                .existsByEmail(registerDto.getEmail())){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"email already exist");
        }
        User user= new User();
        user.setName(registerDto.getName());
        user.setUsername(registerDto.getUsername());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoleSet(roles);
        userRepository.save(user);
        return "User registered successfully!!";
    }
}

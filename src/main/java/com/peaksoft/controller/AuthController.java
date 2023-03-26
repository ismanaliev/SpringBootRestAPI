package com.peaksoft.controller;

import com.peaksoft.service.StudentService;
import com.peaksoft.dto.*;
import com.peaksoft.entity.User;
import com.peaksoft.repository.UserRepository;
import com.peaksoft.security.jwt.JwtTokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/jwt")
@RequiredArgsConstructor
public class AuthController {
    private final StudentService studentService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final LoginMapper loginMapper;
    private final JwtTokenUtil tokenUtil;

    @PostMapping("/register")
    @Operation(description = "all users can register")
    public StudentResponse register(@RequestBody StudentRequest request) {
        return studentService.save(request);
    }

    @PostMapping("/login")
    @Operation(description = "Using username and password, user can sign in")
    public LoginResponse login(@RequestBody LoginRequest request) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(token);
        User user = userRepository.findUserByEmail(token.getName());
        return loginMapper.view(tokenUtil.generatedToken(user), "successful", user);
    }


}

package com.duvanlabrador.parking.Controller;

import com.duvanlabrador.parking.DTO.http.request.LoginRequest;
import com.duvanlabrador.parking.DTO.http.response.LoginResponse;
import com.duvanlabrador.parking.Security.jwt.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@RestController
@RequestMapping("/api/login")
public class AuthController {
    private final AuthenticationManager authManager;

    private final JwtUtils jwtUtils;


    public AuthController(AuthenticationManager authManager, JwtUtils jwtUtils, UserDetailsService userService) {
        this.authManager = authManager;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));

            User user = (User) authentication.getPrincipal();
            String token = jwtUtils.generateAccessToken(user);

            return ResponseEntity.ok(new LoginResponse(true, token));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(false, null));
        }
    }
}

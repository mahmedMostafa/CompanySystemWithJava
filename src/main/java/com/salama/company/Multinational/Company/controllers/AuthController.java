package com.salama.company.Multinational.Company.controllers;


import com.salama.company.Multinational.Company.annotations.IgnoreResponseBinding;
import com.salama.company.Multinational.Company.dtos.AuthenticationResponse;
import com.salama.company.Multinational.Company.dtos.LoginRequest;
import com.salama.company.Multinational.Company.dtos.RegisterRequest;
import com.salama.company.Multinational.Company.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(service.login(request));
    }

    //only for testing to clear the jwt token
    @PostMapping("/logout")
    @IgnoreResponseBinding
    public ResponseEntity<HashMap<String, String>> login() {
        HashMap<String, String> map = new HashMap<>();
        map.put("status", "success");
        map.put("message", "Logged out successfully");
        return ResponseEntity.ok(map);
    }

}

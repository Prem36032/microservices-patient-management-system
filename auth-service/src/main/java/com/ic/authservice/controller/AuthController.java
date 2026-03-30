package com.ic.authservice.controller;

import com.ic.authservice.dto.LoginRequestDto;
import com.ic.authservice.dto.LoginResponseDto;
import com.ic.authservice.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody LoginRequestDto requestDto){

        Optional<String> token = authService.authenticate(requestDto);
        return token.map(s -> ResponseEntity.ok(new LoginResponseDto(s))).orElseGet(() -> new ResponseEntity<>(HttpStatus.UNAUTHORIZED));
    }
    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String token){

        if(token == null || !token.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token format");
        }

        return authService.validateToken(token.substring(7)) ? ResponseEntity.ok("Valid") : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is invalid");

    }
}

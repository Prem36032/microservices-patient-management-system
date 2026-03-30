package com.ic.authservice.service;

import com.ic.authservice.dto.LoginRequestDto;
import com.ic.authservice.dto.LoginResponseDto;
import com.ic.authservice.model.User;
import com.ic.authservice.util.jwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final jwtUtil jwtUtil;

    public Optional<String> authenticate(LoginRequestDto requestDto) {

        if(userService.getUserByEmail(requestDto.getEmail()).isEmpty()){
            log.info("User doesn't exist");
        } else if (!passwordEncoder.matches(requestDto.getPassword(),userService.getUserByEmail(requestDto.getEmail()).get().getPassword())) {
            log.info("Password mismatch");
        }

        String encodedPass= userService.getUserByEmail(requestDto.getEmail()).get().getPassword();

        log.info("Email : {}",requestDto.getEmail());
        log.info("Raw password: {}", requestDto.getPassword());
        log.info("Encoded password: {}",encodedPass);
        log.info("password matches: {}", passwordEncoder.matches(requestDto.getPassword(), encodedPass));

        Optional<String> token = userService.getUserByEmail(requestDto.getEmail())
                .filter(u-> passwordEncoder.matches(requestDto.getPassword(),u.getPassword()))
                .map( u -> jwtUtil.generateToken(u.getEmail(),u.getRole()));
        log.info("Token : {}",String.valueOf(token));
        return token;

    }

    public boolean validateToken(String token) {
        try{
            jwtUtil.validateToken(token);
            return true;
        }
        catch (JwtException e){
            return false;
        }

    }
}

package com.ic.authservice.util;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class jwtUtil {
    private final Key secretkey;

    public jwtUtil(@Value("${jwt.secret}") String secretKey){
        byte[] keybyte = Base64.getDecoder().decode(secretKey);
        this.secretkey= Keys.hmacShaKeyFor(keybyte);
    }


    public String generateToken(String email, String role){
        return Jwts.builder().subject(email).issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + (1000*60*60) )).claim("role",role).signWith(secretkey).compact();
    }

    public void validateToken(String token) {
        try{
            Jwts.parser().verifyWith((SecretKey) secretkey).build().parseSignedClaims(token);
        }
        catch (SignatureException e){
            throw new JwtException("Invalid JWT signature");
        }
        catch (JwtException e){
            throw new JwtException("Invalid jwt");
        }

    }


}

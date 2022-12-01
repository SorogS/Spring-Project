package com.sorog.carrent.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;


import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;
import java.awt.event.KeyListener;
import java.nio.Buffer;
import java.security.Key;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
//import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
@Component
@Log
public class JwtProvider {
    @Value("$(jwt.secret)")
    private String jwtSecret;

    public String generateToken(String login) {
        Date date = Date.from(LocalDate.now().plusDays(15).atStartOfDay(ZoneId.systemDefault()).toInstant());
        //String base64 = Encoders.BASE64.encode(jwtSecret.getBytes());
        //Key jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
        jwtSecret = Encoders.BASE64.encode("asdwadswabhhjghjghkjgkghjghjgghjghjgkghjkhkjfhghklghlghlghggjlghjghjghjlghjghgghjghjkghjkghjkghgghjghjghjkgkhghjghjkghjghjghjghjghjghjgjhgjhkgjhghjkgjkgjhkgjhkghjkgjhkghjkghjkg".getBytes());
        //Key jwtSecretKey = Keys.hmacShaKeyFor(jwtSecret);

        return Jwts.builder()
                .setSubject(login)
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            jwtSecret = Encoders.BASE64.encode("asdwadswabhhjghjghkjgkghjghjgghjghjgkghjkhkjfhghklghlghlghggjlghjghjghjlghjghgghjghjkghjkghjkghgghjghjghjkgkhghjghjkghjghjghjghjghjghjgjhgjhkgjhghjkgjkgjhkgjhkghjkgjhkghjkghjkg".getBytes());
            Jwts.parserBuilder()
                    .setSigningKey(jwtSecret).build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        //Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        jwtSecret = Encoders.BASE64.encode("asdwadswabhhjghjghkjgkghjghjgghjghjgkghjkhkjfhghklghlghlghggjlghjghjghjlghjghgghjghjkghjkghjkghgghjghjghjkgkhghjghjkghjghjghjghjghjghjgjhgjhkgjhghjkgjkgjhkgjhkghjkgjhkghjkghjkg".getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
    }
}
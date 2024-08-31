package com.springboot.blog.security;

import com.springboot.blog.exception.BlogAPIException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //method to create JWT token

    public String jwtToken(Authentication authentication){
        String name = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime() + jwtExpirationDate);
        String token = Jwts.builder().subject(name).issuedAt(currentDate).expiration(expirationDate)
                .signWith(key()).compact();
        return token;
    }

    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    //get username from jwt token
    public String getUsername(String token){

        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parse(token);
        }catch (MalformedJwtException malformedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid Jwt Token");
        }catch (ExpiredJwtException expiredJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Token expired");
        }catch (UnsupportedJwtException unsupportedJwtException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Unsupported JWT token");
        }catch (IllegalArgumentException illegalArgumentException){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"JWT String is empty");

        }
        return true;
    }
}

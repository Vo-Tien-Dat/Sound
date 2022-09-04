package com.music.sound.security;

import java.util.Date;
import java.util.function.Function;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.music.sound.model.CustomUserDetails;
import  io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import com.music.sound.utils.AppConstants;
@Component
public class JwtTokenUtil {
    private static final long serialVersionUID = AppConstants.TIME_TOKEN_VALIDITY;
    public static final long JWT_TOKEN_VALIDITY = AppConstants.TIME_TOKEN_VALIDITY * 1000; 
    private static final String jwtSecret = AppConstants.SECRET_SECURITY;

    public static Long getSerialVersion(){
        return serialVersionUID;
    }

    public String getUsernameFromToken(String token){
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsReolver){
        final Claims claims = getAllClaimsFromToken(token);
        return claimsReolver.apply(claims);
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public String generateToken(Authentication authentication){
        String username = authentication.getPrincipal().toString();
        Date nowDate = new Date(); 
        Date expiryDate = new Date(nowDate.getTime() + JWT_TOKEN_VALIDITY) ; 
        return Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(nowDate)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    
    public Boolean validateToken(String token, CustomUserDetails userDetailsOverwrite ){
        final String username = getUsernameFromToken(token);
        return(username.equals(userDetailsOverwrite.getUsername()) && isTokenExpired(token));
    }

}

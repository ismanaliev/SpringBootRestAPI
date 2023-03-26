package com.peaksoft.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtTokenUtil {
    @Value("java-moscow-4")
    private String jwtSecret;
    private final Long JWT_TOKEN_VALIDITY = 7*24*60*60*1000l; //1 week

    private String createToken(Map<String,Object> claims, String subject){
        return Jwts.builder().setClaims(claims).
                setSubject(subject).
                setIssuedAt(new Date(System.currentTimeMillis())).
                setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY)).
                signWith(SignatureAlgorithm.HS512, jwtSecret).
                compact();
    }
    public String generatedToken(UserDetails userDetails){
        Map<String,Object>claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
    public Date getExpirationDate( String token ){
       return getClaimsByToken(token, Claims::getExpiration);
    }
    public <T> T getClaimsByToken(String token, Function<Claims,T> claims){
       final Claims claims1 = getAllClaimsFromToken(token);
       return claims.apply(claims1);
    }

    public Claims getAllClaimsFromToken(String token){
        return Jwts.parser().setSigningKey(jwtSecret).
                parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDate(token);
        return expiration.before(new Date());
    }

    public String getUsernameFromToken(String token){
        return getClaimsByToken(token, Claims::getSubject);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }
}

package com.example.ecommerce.pms.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JWTService {

    public JWTService() {

//        String secretKey = "jFwonq5Th2E8VUU29smWMhJo8mz4Ada00XFP+GjsokM=";

//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
//            SecretKey sk = keyGen.generateKey();
//            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
    }

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                .and()
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        String secretKey = "ceafd1ccea3e85eee072e7f0dbc645d8c764e18f0046f696473530739d607f1004543810a10e53249d8488bff171c5a2868986740f7080b89fd3707db56d90ceb12e4cdb0e78aa844a76817274ed767bfa3f5505792757ba5fcc3e6799aa4db7f695a460242e0328f7d4f56823bcddb26ab685c77de8a7cac547b572799a669196e0dce8b95661dc62b71855109fe33b6b821bb55347a7e1a2760319b028c245df68c1b7b3acc1157aa1b90b336578a105584b9bee74fff74419fcdfa92c7298edc768c4bae2d732aede04ed597be9d74358ca2612e06f8bad97bf7720f5f34a2a76aab9d5b3725a074fb96694f37ae791027a9946b352eacb863da8dcd5b88c";
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        // extract the username from jwt token
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public void validate(String token) {
        Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}

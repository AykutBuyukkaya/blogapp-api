package com.buyukkaya.blogappapi.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.buyukkaya.blogappapi.security.exception.JwtTokenParsingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class JwtConfiguration {

    @Value("${blogapp.jwt.secretKey}")
    private String jwtSecret;

    @Value("${blogapp.jwt.duration}")
    private int duration;

    private Algorithm algorithm;

    @PostConstruct
    private void generateAlgorithm() {
        algorithm = Algorithm.HMAC256(jwtSecret);
    }

    public String generateToken(Authentication authentication) {

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        //TODO: ADD LOGS HERE.

        return JWT.create().withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + duration))
                .withSubject(userDetails.getUsername())
                .withClaim("role", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList())).
                sign(algorithm);




    }

    public Map<String, Object> parseToken(String jwt) throws JwtTokenParsingException {

        if (jwt == null || !jwt.startsWith("Bearer ")) {

            //TODO: ADD LOGS HERE.
            throw new JWTDecodeException("Invalid jwt token");

        } else {

            String token = jwt.substring("Bearer ".length());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedToken = verifier.verify(token);

            String username = decodedToken.getSubject();
            String[] role = decodedToken.getClaim("role").asArray(String.class);

            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("username", username);
            resultMap.put("role", role);

            return resultMap;

        }
    }
}



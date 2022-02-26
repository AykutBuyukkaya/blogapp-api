package com.buyukkaya.blogappapi.security.config;

import com.buyukkaya.blogappapi.security.jwt.JwtConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
@RequiredArgsConstructor
public class CustomRequestFilter extends OncePerRequestFilter {

    private final JwtConfiguration jwtConfiguration;
    private final UserDetailsService userDetailsService;


    //This method will filter every incoming request unless it is permitted to anonymous from security config.
    //This method will look for an Authorization header and parse the token if it exists.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(AUTHORIZATION);

        try {

            Map<String, Object> parsedToken = jwtConfiguration.parseToken(jwt);
            userDetailsService.loadUserByUsername((String) parsedToken.get("username"));

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream((String[]) parsedToken.get("role")).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(parsedToken.get("username"), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            log.error("Error while filtering request with error message {}", e.getMessage());
            response.setHeader("error", e.getMessage());
            response.sendError(403);

        }

    }
}

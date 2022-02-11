package com.buyukkaya.blogappapi.security.config;

import com.buyukkaya.blogappapi.security.jwt.JwtConfiguration;
import lombok.RequiredArgsConstructor;
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

@RequiredArgsConstructor
public class CustomRequestFilter extends OncePerRequestFilter {

    private final JwtConfiguration jwtConfiguration;
    private final UserDetailsService userDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(AUTHORIZATION);

        try {

            Map<String, Object> tokenMap = jwtConfiguration.parseToken(jwt);
            userDetailsService.loadUserByUsername((String) tokenMap.get("username"));

            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            stream((String[]) tokenMap.get("role")).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(tokenMap.get("username"), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {

            //TODO: ADD LOGS HERE.
            response.setHeader("error", e.getMessage());
            response.sendError(403);

        }

    }
}

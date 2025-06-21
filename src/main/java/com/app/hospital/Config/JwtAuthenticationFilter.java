package com.app.hospital.Config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private JwtUtils jwtUtils;

    /**  ⏩  Skip /api/auth/** */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest req) {
        return req.getServletPath().startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain)
            throws ServletException, IOException {

        String token = resolveToken(req);
        if (token != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            String username = jwtUtils.extractUsername(token);
            String role     = jwtUtils.extractRole(token);        // ⭐ get ROLE_ADMIN

            if (jwtUtils.validateToken(token, username)) {
                var auth = new UsernamePasswordAuthenticationToken(
                        username,                          // principal
                        null,                              // credentials
                        List.of(new SimpleGrantedAuthority(role))  // authority from JWT
                );
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        chain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest req) {
        String h = req.getHeader("Authorization");
        return (StringUtils.hasText(h) && h.startsWith("Bearer "))
                ? h.substring(7) : null;
    }
}

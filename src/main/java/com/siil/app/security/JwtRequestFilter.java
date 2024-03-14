package com.siil.app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        String role = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
            role = jwtUtil.extractRole(jwt); // Assurez-vous d'implémenter cette méthode dans JwtUtil
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                Claims claims = jwtUtil.extractAllClaims(jwt);
                String userRole = claims.get("role", String.class);
                if (isRoleAllowed(userRole, request)) {
                    UsernamePasswordAuthenticationToken authenticationToken = 
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Accès non autorisé");
                    return;
                }
            }
        }
        chain.doFilter(request, response);
    }

    private boolean isRoleAllowed(String role, HttpServletRequest request) {
        String requestURL = request.getRequestURI();

        // Vérifier l'accès pour les administrateurs
        if (requestURL.startsWith("/api/admin/") && !role.equals("ROLE_Admin")) {
            return false; // Bloquer l'accès si l'utilisateur n'est pas un administrateur
        }

        // Vérifier l'accès pour les utilisateurs généraux
        if (requestURL.contains("/api/utilisateur/") && !role.equals("ROLE_Utilisateur")) {
            return false; // Bloquer l'accès si l'utilisateur n'a pas le rôle "Utilisateur"
        }
        

        return true; // Autoriser l'accès si aucun des cas ci-dessus ne s'applique
    }
}

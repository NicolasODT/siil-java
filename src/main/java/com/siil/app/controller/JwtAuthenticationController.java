package com.siil.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siil.app.model.JwtRequest;
import com.siil.app.model.JwtResponse;
import com.siil.app.security.CustomUserDetails;
import com.siil.app.security.JwtUtil;
import com.siil.app.service.CustomUserDetailsService;

@RestController
@RequestMapping("/api/utilisateurs")
public class JwtAuthenticationController {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) {
        log.info("Attempting to authenticate user: {}", authenticationRequest.getUsername());

        try {
            // Tente d'authentifier l'utilisateur avec les informations fournies
            Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(), 
                    authenticationRequest.getPassword()
                )
            );

            // Récupère les détails de l'utilisateur authentifié
            CustomUserDetails customUserDetails = (CustomUserDetails) authenticate.getPrincipal();
            log.info("User authenticated successfully: {}", authenticationRequest.getUsername());

            // Génère le JWT à partir des détails de l'utilisateur
            final String jwt = jwtTokenUtil.generateToken(customUserDetails);

            // Retourne le JWT dans la réponse
            return ResponseEntity.ok(new JwtResponse(jwt));
        } catch (Exception e) {
            // Logge l'erreur si l'authentification échoue
            log.error("Erreur d'authentification pour l'utilisateur : {}", authenticationRequest.getUsername(), e);
            // Renvoie une réponse avec un statut 401 Unauthorized
            log.error("Authentication failed for user: {}", authenticationRequest.getUsername(), e);

            return ResponseEntity.status(401).body("Échec de l'authentification");
        }
    }
}

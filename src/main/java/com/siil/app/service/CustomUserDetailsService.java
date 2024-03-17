package com.siil.app.service;

import com.siil.app.model.Utilisateur;
import com.siil.app.repository.UtilisateurRepository;
import com.siil.app.security.CustomUserDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);
    private final UtilisateurRepository utilisateurRepository;

    @Autowired
    public CustomUserDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Loading user by username: {}", email);
        Utilisateur utilisateur = utilisateurRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur trouvé avec l'email : " + email));

        log.info("Utilisateur trouvé avec l'email : {}", email); // Log pour confirmer la récupération de l'utilisateur

        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setId(utilisateur.getId());
        customUserDetails.setUsername(utilisateur.getEmail());
        customUserDetails.setPassword(utilisateur.getPassword());
        customUserDetails.setRole(utilisateur.getRole().name());
        customUserDetails.setAuthorities(utilisateur.getRole().getAuthorities());
        
        return customUserDetails;
    }
}

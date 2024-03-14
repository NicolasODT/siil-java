package com.siil.app.service;

import com.siil.app.model.Utilisateur;
import com.siil.app.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
        String hashedPassword = passwordEncoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(hashedPassword);
        return utilisateurRepository.save(utilisateur);
    }

    public boolean deleteUtilisateur(Long id) {
        if (utilisateurRepository.existsById(id)) {
            utilisateurRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Optional<Utilisateur> updateUtilisateur(Long id, Utilisateur utilisateurDetails) {
        Optional<Utilisateur> utilisateurExistant = utilisateurRepository.findById(id);
        if (!utilisateurExistant.isPresent()) {
            return Optional.empty();
        }
        Utilisateur utilisateur = utilisateurExistant.get();
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setPrenom(utilisateurDetails.getPrenom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        utilisateur.setRole(utilisateurDetails.getRole());

        // Hasher le mot de passe si fourni
        if (utilisateurDetails.getPassword() != null && !utilisateurDetails.getPassword().isEmpty()) {
            String hashedPassword = passwordEncoder.encode(utilisateurDetails.getPassword());
            utilisateur.setPassword(hashedPassword);
        }
        
        utilisateurRepository.save(utilisateur);
        return Optional.of(utilisateur);
    }

    public Optional<Utilisateur> verifyLogin(String email, String rawPassword) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        if (utilisateur.isPresent() && passwordEncoder.matches(rawPassword, utilisateur.get().getPassword())) {
            return utilisateur;
        } else {
            return Optional.empty();
        }
    }
}

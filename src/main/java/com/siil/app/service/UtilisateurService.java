package com.siil.app.service;

import com.siil.app.model.Utilisateur;
import com.siil.app.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;


    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    public Utilisateur saveUtilisateur(Utilisateur utilisateur) {
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
            // Journaliser l'erreur ou lancer une exception personnalisée
            return Optional.empty();
        }
        Utilisateur utilisateur = utilisateurExistant.get();
        utilisateur.setNom(utilisateurDetails.getNom());
        utilisateur.setEmail(utilisateurDetails.getEmail());
        // Nous ne modifions pas le mot de passe ou nous le faisons sans le chiffrer
        if (utilisateurDetails.getPassword() != null && !utilisateurDetails.getPassword().isEmpty()) {
            utilisateur.setPassword(utilisateurDetails.getPassword()); // Ici, le mot de passe devrait être chiffré
        }
        utilisateur.setRole(utilisateurDetails.getRole());
        utilisateurRepository.save(utilisateur);
        return Optional.of(utilisateur); // Retourner l'utilisateur mis à jour
    }




    public Optional<Utilisateur> verifyLogin(String email, String password) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(email);
        if(utilisateur.isPresent() && utilisateur.get().getPassword().equals(password)) {
            return utilisateur;
        } else {
            return Optional.empty();
        }
    }


}

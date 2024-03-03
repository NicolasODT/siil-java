package com.siil.app.controller;

import com.siil.app.model.LoginRequest;
import com.siil.app.model.Utilisateur;
import com.siil.app.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.siil.app.model.LoginResponse;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Récupérer tous les utilisateurs
    @GetMapping
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurService.getAllUtilisateurs();
    }

    // Récupérer un utilisateur par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable(value = "id") Long utilisateurId) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(utilisateurId);

        return utilisateur.map(response -> ResponseEntity.ok().body(response))
                          .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Créer un nouvel utilisateur
    @PostMapping
    public Utilisateur createUtilisateur(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.saveUtilisateur(utilisateur);
    }

    // Mettre à jour un utilisateur
    @PutMapping("/{id}")
    public ResponseEntity<Utilisateur> updateUtilisateur(@PathVariable(value = "id") Long utilisateurId,
                                                         @RequestBody Utilisateur utilisateurDetails) {
        return utilisateurService.updateUtilisateur(utilisateurId, utilisateurDetails)
                                 .map(updatedUtilisateur -> ResponseEntity.ok(updatedUtilisateur))
                                 .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Supprimer un utilisateur
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUtilisateur(@PathVariable(value = "id") Long utilisateurId) {
        boolean isDeleted = utilisateurService.deleteUtilisateur(utilisateurId);
        if (isDeleted) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Login d'un utilisateur
    @CrossOrigin
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {
        Optional<Utilisateur> utilisateurOpt = utilisateurService.verifyLogin(loginRequest.getEmail(), loginRequest.getPassword());

        if (utilisateurOpt.isPresent()) {
            Utilisateur utilisateur = utilisateurOpt.get();
            LoginResponse response = new LoginResponse();
            response.setMessage("Login réussi.");
            response.setUserId(utilisateur.getId());
            response.setNom(utilisateur.getNom());
            response.setPrenom(utilisateur.getPrenom());
            response.setEmail(utilisateur.getEmail());
            response.setRole(utilisateur.getRole());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"error\": \"Échec de l'authentification.\"}");
        }
    }



    @PostMapping("/register")
    public Utilisateur createUtilisateur1(@RequestBody Utilisateur utilisateur) {
        return utilisateurService.saveUtilisateur(utilisateur);
    }

}

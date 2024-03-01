package com.siil.app.repository;

import com.siil.app.model.Utilisateur;
import com.siil.app.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    // Trouver un utilisateur par son email
    Optional<Utilisateur> findByEmail(String email);

    // Trouver des utilisateurs par nom
    List<Utilisateur> findByNom(String nom);
    
    List<Utilisateur> findByNomContaining(String prenom);

    // Trouver des utilisateurs par rôle
    List<Utilisateur> findByRole(Role role); 
}

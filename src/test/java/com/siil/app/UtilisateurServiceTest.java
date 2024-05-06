package com.siil.app;

import com.siil.app.model.Utilisateur;
import com.siil.app.model.Role;
import com.siil.app.repository.UtilisateurRepository;
import com.siil.app.service.UtilisateurService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UtilisateurServiceTest {
    @InjectMocks
    private UtilisateurService utilisateurService;
    @Mock
    private UtilisateurRepository utilisateurRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testUpdateUtilisateur() {
        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setNom("Test");
        utilisateur.setPrenom("User");
        utilisateur.setEmail("test.user@example.com");

        utilisateur.setRole(Role.Utilisateur);

        utilisateur.setPassword("password");

        when(utilisateurRepository.findById(any(Long.class))).thenReturn(Optional.of(utilisateur));
        when(utilisateurRepository.save(any(Utilisateur.class))).thenReturn(utilisateur);
        when(passwordEncoder.encode(any(String.class))).thenReturn("hashedPassword");

        Optional<Utilisateur> updatedUtilisateur = utilisateurService.updateUtilisateur(1L, utilisateur);

        verify(utilisateurRepository, times(1)).findById(any(Long.class));
        verify(utilisateurRepository, times(1)).save(any(Utilisateur.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));

        assertEquals("Test", updatedUtilisateur.get().getNom());
        assertEquals("User", updatedUtilisateur.get().getPrenom());
        assertEquals("test.user@example.com", updatedUtilisateur.get().getEmail());
        assertEquals(Role.Utilisateur, updatedUtilisateur.get().getRole());
        assertEquals("hashedPassword", updatedUtilisateur.get().getPassword());
    }
}
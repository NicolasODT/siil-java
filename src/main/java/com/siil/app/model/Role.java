package com.siil.app.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;


public enum Role {
    Admin,
    Manager,
    Livreur,
    Utilisateur;

    public Set<GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + this.name()));
    }
}

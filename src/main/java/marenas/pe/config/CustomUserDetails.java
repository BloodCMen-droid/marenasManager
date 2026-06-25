package marenas.pe.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import marenas.pe.model.UsuarioCredential;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private UsuarioCredential usuarioCredential;

    public CustomUserDetails(UsuarioCredential userCredential) {
        this.usuarioCredential = userCredential;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	String nombreRol = usuarioCredential.getRol().getNombre();
        return List.of(new SimpleGrantedAuthority(nombreRol));
    }

    @Override
    public String getUsername() {
        return usuarioCredential.getEmail();
    }

    @Override
    public String getPassword() {
        return usuarioCredential.getPassword();
    }

    public String getNombreEmpleado() {
        return usuarioCredential.getEmpleado().getNombre();
    }
    
    public UsuarioCredential getUsuarioCredential() {
        return usuarioCredential;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

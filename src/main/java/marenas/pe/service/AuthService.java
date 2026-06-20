package marenas.pe.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import marenas.pe.model.UsuarioCredential;
import marenas.pe.repository.IUsuarioCredentialRepository;




@Service
public class AuthService {
    @Autowired
    private IUsuarioCredentialRepository repository; 

    @Autowired
    private PasswordEncoder passwordEncoder; //SpringSecurity


    public String saveUser(UsuarioCredential credential) {
        try{
            credential.setPassword(passwordEncoder.encode(credential.getPassword()));
            repository.save(credential);
            return "Usuario registrado.";
        } catch (Exception e) {
            System.out.print("Usuario no registrado:".concat(e.getMessage()));
            return "Usuario No registrado.";
        }
    }

} 

package marenas.pe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import marenas.pe.model.UsuarioCredential;


public interface IUsuarioCredentialRepository extends JpaRepository<UsuarioCredential, Long> {

	Optional<UsuarioCredential>  findByEmail(String email);

}

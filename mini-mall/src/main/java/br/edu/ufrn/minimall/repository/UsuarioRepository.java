package br.edu.ufrn.minimall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import br.edu.ufrn.minimall.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario , Long> {

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByLogin(String username);
}




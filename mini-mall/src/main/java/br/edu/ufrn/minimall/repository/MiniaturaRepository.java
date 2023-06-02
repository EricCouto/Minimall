package br.edu.ufrn.minimall.repository;

import br.edu.ufrn.minimall.model.Miniatura;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MiniaturaRepository extends JpaRepository<Miniatura, Long> {
    List<Miniatura> findByDeletedIsNull();
}

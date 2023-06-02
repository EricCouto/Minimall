package br.edu.ufrn.minimall.service;

import org.springframework.stereotype.Service;
import br.edu.ufrn.minimall.model.Miniatura;
import br.edu.ufrn.minimall.repository.MiniaturaRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MiniaturaService {

    private final MiniaturaRepository repository;

    public MiniaturaService(MiniaturaRepository repository) {
        this.repository = repository;
    }

    public Miniatura create(Miniatura m) {
        return repository.save(m);
    }

    public Miniatura update(Miniatura m) {
        return repository.saveAndFlush(m);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Miniatura> findByDeletedIsNull() {
        return repository.findByDeletedIsNull();
    }

    public Miniatura findById(Long id) {
        Optional<Miniatura> MiniaturaOptional = repository.findById(id);
        return MiniaturaOptional.orElse(null);
    }

    public List<Miniatura> findAll() {
        return repository.findAll();
    }
}
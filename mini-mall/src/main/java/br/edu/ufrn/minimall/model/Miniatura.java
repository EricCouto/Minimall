package br.edu.ufrn.minimall.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Miniatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String Nome;
    String Tipo;
    String descricao;
    String preco;
    String imagem;
    Date deleted;
}


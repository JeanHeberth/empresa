package com.br.empresa.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Departamento extends GenericDomain {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String numero;


    public Departamento(Long id, String numero, LocalDate dataCadastro) {
        this.setNome(numero);
        this.setId(id);
        this.setNumero(numero);
    }


}

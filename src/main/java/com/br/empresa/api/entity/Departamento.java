package com.br.empresa.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Departamento extends GenericDomain {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Long numero;

    public Departamento(int id, String numero) {
        this.setNome(numero);
        this.setId((long) id);
    }


}

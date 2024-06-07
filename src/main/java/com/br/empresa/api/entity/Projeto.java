package com.br.empresa.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Projeto extends GenericDomain {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Double valorProjeto;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column(nullable = false)
    private LocalDate dataFinal;

    @ManyToOne
    private Departamento departamento;
}

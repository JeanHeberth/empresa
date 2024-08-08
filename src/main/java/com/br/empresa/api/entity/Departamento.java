package com.br.empresa.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

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

    @OneToMany(mappedBy = "departamento")
    private List<Funcionario> funcionarios;


}

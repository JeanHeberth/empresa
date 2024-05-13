package com.br.empresa.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Funcionario extends GenericDomain {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

//    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;


    private String telefone;

    private LocalDate dataNascimento;

    private char sexo;

    private Double salario;

    @ManyToOne
    private Funcionario supervisor;

    public static FuncionarioBuilder builder() {
        return new FuncionarioBuilder();
    }

    public static class FuncionarioBuilder {
        private Long id;
        private String nome;
        private String cpf;
        private String telefone;
        private LocalDate dataNascimento;
        private char sexo;
        private Double salario;
    }
}

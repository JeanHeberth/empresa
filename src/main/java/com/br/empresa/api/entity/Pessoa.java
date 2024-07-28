package com.br.empresa.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Pessoa extends GenericDomain {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    private String rg;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;

    private char sexo;

    @OneToOne(mappedBy = "pessoa", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Funcionario funcionario;

    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}

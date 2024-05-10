package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
public class FuncionarioRequestDto {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String senha;

    private String telefone;

    private LocalDate dataNascimento;

    private char sexo;

    private Double salario;
}

package com.br.empresa.api.dto;

import com.br.empresa.api.entity.Funcionario;
import lombok.Data;

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

    private Funcionario supervisor;

}

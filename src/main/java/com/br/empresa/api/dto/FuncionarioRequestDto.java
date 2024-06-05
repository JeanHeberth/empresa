package com.br.empresa.api.dto;

import com.br.empresa.api.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioRequestDto {

    private Long id;

    private String nome;

    private String cpf;

    private String endereco;

    private String email;

    private String senha;

    private String telefone;

    private LocalDate dataNascimento;

    private char sexo;

    private Double salario;

    private Funcionario supervisor;

    public FuncionarioRequestDto(String nome, String email, String numero) {
    }
}

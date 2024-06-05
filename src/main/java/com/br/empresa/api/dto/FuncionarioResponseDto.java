package com.br.empresa.api.dto;


import com.br.empresa.api.entity.Funcionario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FuncionarioResponseDto {

    private Long id;

    private String nome;

    private String cpf;

    private String endereco;

    private String email;

    private String telefone;

    private LocalDate dataNascimento;

    private char sexo;

    private Double salario;

    public FuncionarioResponseDto(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.endereco = funcionario.getEndereco();
        this.email = funcionario.getEmail();
        this.telefone = funcionario.getTelefone();
        this.dataNascimento = funcionario.getDataNascimento();
        this.sexo = funcionario.getSexo();
        this.salario = funcionario.getSalario();
    }


    public FuncionarioResponseDto(long id, String nome, String mail) {
    }
}

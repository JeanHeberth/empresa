package com.br.empresa.api.dto;


import com.br.empresa.api.entity.Funcionario;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String email;
    private String telefone;
    private String cargo;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataNascimento;
    private char sexo;
    private Double salario;
    private Long idSupervisor;
    private Long idEndereco;


    public FuncionarioResponseDto(Funcionario funcionario) {
        this.id = funcionario.getId();
        this.nome = funcionario.getNome();
        this.cpf = funcionario.getCpf();
        this.email = funcionario.getEmail();
        this.telefone = funcionario.getTelefone();
        this.dataNascimento = funcionario.getDataNascimento();
        this.sexo = funcionario.getSexo();
        this.cargo = funcionario.getCargo();
        this.salario = funcionario.getSalario();
        this.idSupervisor = (funcionario.getSupervisor() != null) ? funcionario.getSupervisor().getId() : null;
        this.idEndereco = funcionario.getEndereco().getId();
    }


    public FuncionarioResponseDto(long id, String nome, String mail) {
    }
}

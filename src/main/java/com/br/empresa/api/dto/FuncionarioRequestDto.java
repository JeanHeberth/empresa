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
public class FuncionarioRequestDto {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String cargo;

    private String telefone;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private char sexo;
    private Double salario;
    private Long idSupervisor;
    private Long idEndereco;

    public FuncionarioRequestDto(String nome, String email, String numero) {
    }
}

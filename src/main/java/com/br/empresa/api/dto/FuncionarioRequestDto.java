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
    private String matricula;
    private String cpf;
    private String emailCorporativo;
    private String cargo;
    private Double salario;
    private Long idSupervisor;
    private Long idPessoa;


    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataAdmissao;

}

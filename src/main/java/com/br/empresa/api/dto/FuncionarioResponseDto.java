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
    private Long matricula;
    private String emailCorporativo;
    private String cargo;
    private Double salario;
    private Long idSupervisor;
    private Long idPessoa;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;


}

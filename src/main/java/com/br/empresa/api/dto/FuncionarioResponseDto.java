package com.br.empresa.api.dto;


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
    private String matricula;
    private String emailCorporativo;
    private String cargo;
    private Double salario;
    private String nomePessoa;
    private String nomeSupervisor;
    private String nomeDepartamento;
    private Long supervisorId;


    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;


}

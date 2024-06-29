package com.br.empresa.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoRequestDto {

    private Long id;

    private String nome;

    private Long numero;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;


    public DepartamentoRequestDto(String nome, String numero) {
    }

    public DepartamentoRequestDto(Long id, String nome, String numero) {
    }
}

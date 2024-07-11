package com.br.empresa.api.dto;

import com.br.empresa.api.entity.Departamento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentoResponseDto {

    private Long id;

    private String nome;

    private String numero;


    public DepartamentoResponseDto(Departamento departamento) {
        this.id = departamento.getId();
        this.nome = departamento.getNome();
        this.numero = departamento.getNumero();
    }


}

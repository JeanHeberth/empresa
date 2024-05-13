package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoRequestDto {

    private Long id;

    private String nome;

    private String numero;

    public DepartamentoRequestDto(String nome, int numero) {
        this.setNome(nome);
        this.setNumero(String.valueOf(numero));
    }
}

package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DepartamentoRequestDto {

    private Long id;

    private String nome;

    private Long numero;

    public DepartamentoRequestDto(Long id, String nome, Long numero) {
        this.setId(id);
        this.setNome(nome);
        this.setNumero(numero);
    }

}

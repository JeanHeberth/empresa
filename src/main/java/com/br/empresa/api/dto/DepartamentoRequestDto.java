package com.br.empresa.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartamentoRequestDto {

    @NotBlank(message = "O nome do departamento é obrigatório.")
    private String nome;

    @NotNull(message = "O número do departamento é obrigatório.")
    private Long numero;


}
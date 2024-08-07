package com.br.empresa.api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PessoaRequestDto {

    private Long id;
    private String nome;
    private String cpf;
    private String email;
    private String telefone;
    private String rg;
    private Long idEndereco;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;
    private char sexo;


}

package com.br.empresa.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoRequestDto {

    private Long id;
    private String pais;
    private String uf;
    private String cidade;
    private String rua;
    private String cep;
    private String quadra;
    private String casa;
    private String complemento;
    private String estado;
    private String bairro;
    private String logradouro;



}

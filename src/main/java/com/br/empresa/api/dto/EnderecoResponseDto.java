package com.br.empresa.api.dto;

import com.br.empresa.api.entity.Funcionario;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoResponseDto {

    private Long id;
    private String cidade;
    private String cep;
    private String casa;
    private String complemento;
    private String estado;
    private String bairro;
    private String logradouro;



}

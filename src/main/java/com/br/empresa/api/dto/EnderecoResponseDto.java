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
    private String pais;
    private String uf;
    private String cidade;
    private String rua;
    private String cep;

}

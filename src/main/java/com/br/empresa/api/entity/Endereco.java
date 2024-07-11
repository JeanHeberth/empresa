package com.br.empresa.api.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Endereco extends GenericDomain {

    private String pais;
    private String uf;
    private String rua;
    private String quadra;
    private String logradouro;
    private String complemento;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String bairro;

    @Column(nullable = false)
    private String cidade;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    @NotNull
    private String casa;

    @OneToOne(mappedBy = "endereco")
    private Pessoa pessoa;
}

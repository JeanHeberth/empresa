package com.br.empresa.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Departamento extends GenericDomain {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataCadastro;

//    @PrePersist
//    public void prePersist() {
//        setDataCadastro(LocalDate.now(ZoneId.of("America/Sao_Paulo")));
//    }

    public Departamento(Long id, String numero, LocalDate dataCadastro) {
        this.setNome(numero);
        this.setId(id);
        this.setNumero(numero);
        this.setDataCadastro(dataCadastro);
    }


}

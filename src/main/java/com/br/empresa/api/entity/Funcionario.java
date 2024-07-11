package com.br.empresa.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Funcionario extends GenericDomain {

    @Column(nullable = false)
    private Long matricula;

    @Column(nullable = false, unique = true)
    private String emailCorporativo;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    private String cargo;

    private Double salario;

    @ManyToOne
    private Funcionario supervisor;

    @OneToOne
    @JoinColumn(name = "pessoa_id")
    private Pessoa pessoa;

    @OneToMany(mappedBy = "supervisor")
    private List<Funcionario> subordinados;


    public void adicionarSubordinados(Funcionario funcionario) {
        getSubordinados().add(funcionario);
        funcionario.setSupervisor(this);
    }

    public List<Funcionario> getSubordinados() {
        if (subordinados == null) {
            subordinados = new ArrayList<>();
        }
        return subordinados;
    }

}

package com.br.empresa.api.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
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
    private String matricula;

    @Column(nullable = false, unique = true)
    private String emailCorporativo;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataAdmissao;

    private String cargo;

    private Double salario;

    @ManyToOne
    @JoinColumn(name = "supervisor")
    private Funcionario supervisor;

    @ManyToOne
    @JoinColumn(name = "pessoa")
    private Pessoa pessoa;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    public void adicionarSubordinados(Funcionario funcionario) {
        getSubordinados().add(funcionario);
        funcionario.setSupervisor(this);
    }

    @OneToMany(mappedBy = "supervisor")
    private List<Funcionario> subordinados;


    public List<Funcionario> getSubordinados() {
        if (subordinados == null) {
            subordinados = new ArrayList<>();
        }
        return subordinados;
    }

}

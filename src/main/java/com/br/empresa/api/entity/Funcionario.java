package com.br.empresa.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String nome;

    @Column(nullable = false)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    private String telefone;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    private char sexo;

    private Double salario;

    @ManyToOne
    private Funcionario supervisor;

    @OneToOne
    private Endereco endereco;

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
    public Funcionario(String nome, String email, String telefone, String salario) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.salario = Double.valueOf(salario);
    }


}

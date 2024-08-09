package com.br.empresa.api.repository;

import com.br.empresa.api.entity.Endereco;
import com.br.empresa.api.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    Optional<Funcionario> findByEmailCorporativo(String emailCorporativo);

    Optional<Funcionario> findByMatricula(String matriculaSupervisor);

//    Optional<Funcionario> findByMatricula(String matricula);

}

package com.br.empresa.api.repository;

import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.entity.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

}

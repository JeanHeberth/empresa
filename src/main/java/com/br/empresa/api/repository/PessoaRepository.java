package com.br.empresa.api.repository;

import com.br.empresa.api.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    List<Pessoa> findByCpf(String cpf);
}

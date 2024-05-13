package com.br.empresa.api.controller;

import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class DepartamentoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private DepartamentoRepository departamentoRepository;

    @Test
    public void testBuscarTodosDepartamentos() {
        // Criar e persistir departamentos de teste
        Departamento dep1 = new Departamento("TI", "001");
        Departamento dep2 = new Departamento("RH", "002");
        entityManager.persist(dep1);
        entityManager.persist(dep2);
        entityManager.flush();

        // Testar o método findAll
        List<Departamento> departamentos = departamentoRepository.findAll();
        assertThat(departamentos).hasSize(2).extracting(Departamento::getNome).containsOnly(dep1.getNome(), dep2.getNome());
    }
}

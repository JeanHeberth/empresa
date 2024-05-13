package com.br.empresa.api.controller;

import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import com.br.empresa.api.service.DepartamentoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@ActiveProfiles("test")
public class DepartamentoRepositoryTest {

    @Mock
    private DepartamentoService departamentoService;

    @InjectMocks
    private DepartamentoController departamentoController;

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

    @Test
    public void testBuscarDepartamentoPorId() {
        Long departamentoId = 1L;

        // Entrada
        DepartamentoResponseDto departamentoResponseDto = new DepartamentoResponseDto(departamentoId, "TI", "123");
        when(departamentoService.buscarDepartamentoPorId(departamentoId)).thenReturn(departamentoResponseDto);

        // Ação
        ResponseEntity<DepartamentoResponseDto> responseDtoResponseEntity = departamentoController.buscarDepartamentoPorId(departamentoId);

        // Resultado
        assertThat(responseDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseDtoResponseEntity.getBody()).isEqualTo(departamentoResponseDto);
    }

    @Test
    public void testApagarDepartamentoPorId() {
        Long departamentoId = 1L;

        //Ação
        departamentoController.apagarDepartamento(departamentoId);

        // Resultado
        verify(departamentoService, times(1)).apagarDepartamento(departamentoId);
    }

    @Test
    public void testCadastrarDepartamento() {

        // Entrada
        DepartamentoRequestDto departamentoRequestDto = new DepartamentoRequestDto("TI", "123");
        DepartamentoResponseDto departamentoResponseDto = new DepartamentoResponseDto(1L, "TI", "123");
        when(departamentoService.cadastrarDepartamento(any(DepartamentoRequestDto.class))).thenReturn(departamentoResponseDto);

        // Ação
        ResponseEntity<DepartamentoResponseDto> responseDtoResponseEntity = departamentoController.cadastrarDepartamento(departamentoRequestDto);

        //Resultado
        assertThat(responseDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseDtoResponseEntity.getBody()).isEqualTo(departamentoResponseDto);

    }

    @Test
    public void testAtualizarDepartamento() {

        // Entrada
        DepartamentoRequestDto departamentoRequestDto = new DepartamentoRequestDto(1L, "TI Alterado", "123");
        DepartamentoResponseDto departamentoResponseDto = new DepartamentoResponseDto(1L, "TI Aterado", "123");
        when(departamentoService.cadastrarDepartamento(any(DepartamentoRequestDto.class))).thenReturn(departamentoResponseDto);

        // Ação
        ResponseEntity<DepartamentoResponseDto> responseDtoResponseEntity = departamentoController.cadastrarDepartamento(departamentoRequestDto);

        //Resultado
        assertThat(responseDtoResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseDtoResponseEntity.getBody()).isEqualTo(departamentoResponseDto);

    }
}

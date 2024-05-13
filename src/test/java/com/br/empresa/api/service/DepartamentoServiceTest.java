package com.br.empresa.api.service;

import com.br.empresa.api.dto.DepartamentoRequestDto;
import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    @Test
    public void testBuscarDepartamentoPorIdEncontrado() {

        int departamentoId = 1;
        Departamento departamento = new Departamento(Long.valueOf(departamentoId), "TI");
        when(departamentoRepository.findById((long) departamentoId)).thenReturn(Optional.of(departamento));

        // Executar
        DepartamentoResponseDto resultado = departamentoService.buscarDepartamentoPorId((long) departamentoId);

        // Verificar
        assertNotNull(resultado);
        assertEquals(departamentoId, resultado.getId());
        assertEquals("TI", resultado.getNome());

        // Verifica chamadas aos mocks
        verify(departamentoRepository, times(1)).findById(Long.valueOf(departamentoId));
    }

    @Test
    public void testBuscarDepartamentoPorIdNaoEncontrado() {

        // Entrada
        Long departamentoId = 1L;

        // Acao
        Mockito.when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.empty());

        // Rsultado
        assertThrows(RuntimeException.class, () -> departamentoService.buscarDepartamentoPorId(departamentoId));
    }

    @Test
    public void testCadastrarDepartamento() {
        // Dados de entrada
        DepartamentoRequestDto requestDto = new DepartamentoRequestDto();
        requestDto.setNome("TI");
        requestDto.setNumero(123L);

        Departamento departamento = Departamento.builder()
                .nome(requestDto.getNome())
                .numero(String.valueOf(requestDto.getNumero()))
                .build();

        Departamento departamentoSalvo = new Departamento();
        departamentoSalvo.setId(departamentoSalvo.getId());
        departamentoSalvo.setNome(departamentoSalvo.getNome());
        departamentoSalvo.setNumero(departamentoSalvo.getNumero());

        when(departamentoRepository.save(any(Departamento.class))).thenReturn(departamentoSalvo);

        // Execução
        DepartamentoResponseDto resultado = departamentoService.cadastrarDepartamento(requestDto);

        // Verificações
        assertNotNull(resultado);
        assertEquals(departamentoSalvo.getId(), resultado.getId());
        assertEquals(departamentoSalvo.getNome(), resultado.getNome());
        assertEquals(departamentoSalvo.getNumero(), resultado.getNumero());

        // Verifica se o método save foi chamado
        verify(departamentoRepository, times(1)).save(any(Departamento.class));
    }

    @Test
    public void testApagarDepartamento() {
        // Mock data
        Long departamentoId = 1L;
        Departamento departamento = new Departamento(Long.valueOf(1), "TI");

        // Mock repository
        Mockito.when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento));

        // Call the method
        departamentoService.apagarDepartamento(departamentoId);

        // Verify that deleteById is called with the correct ID
        Mockito.verify(departamentoRepository, Mockito.times(1)).deleteById(departamentoId);
    }

    @Test
    public void testAtualizarDepartamento() {
        // Mock data
        Long departamentoId = 1L;
        DepartamentoRequestDto requestDto = new DepartamentoRequestDto(1L, "Financeiro", 2L);
        Departamento departamento = new Departamento(Long.valueOf(1), "TI");
        departamento.setId(departamentoId);

        // Mock repository
        Mockito.when(departamentoRepository.findById(departamentoId)).thenReturn(Optional.of(departamento));
        Mockito.when(departamentoRepository.save(departamento)).thenReturn(departamento);

        // Call the method
        DepartamentoResponseDto result = departamentoService.atualizarDepartamento(requestDto);

        // Verify the result
        assertEquals(departamentoId, result.getId());
        assertEquals("Financeiro", result.getNome());
        assertEquals(2, Long.valueOf(result.getNumero()));
    }

    @Test
    public void testAtualizarDepartamentoFalha() {
        // Dados de entrada
        DepartamentoRequestDto requestDto = new DepartamentoRequestDto();
        requestDto.setId(2L);

        when(departamentoRepository.findById(requestDto.getId())).thenReturn(Optional.empty());

        // Ação e Verificação
        assertThrows(EntityNotFoundException.class, () -> {
            departamentoService.atualizarDepartamento(requestDto);
        });

        // Verificação se findById foi chamado
        verify(departamentoRepository).findById(requestDto.getId());
        verify(departamentoRepository, never()).save(any(Departamento.class));
    }
}




package com.br.empresa.api.service;

import com.br.empresa.api.dto.DepartamentoResponseDto;
import com.br.empresa.api.entity.Departamento;
import com.br.empresa.api.repository.DepartamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    @Test
    public void testBuscarDepartamentoPorIdEncontrado() {

        int departamentoId = 1;
        Departamento departamento = new Departamento(departamentoId, "TI");
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
        // Preparar dados
//        int departamentoId = 2;
//        when(departamentoRepository.findById((long)departamentoId)).thenReturn(Optional.empty());
//
//        // Executar
//        DepartamentoResponseDto resultado = departamentoService.buscarDepartamentoPorId((long)departamentoId);
//
//
//        // teste
//        assertNull(departamentoId);
//        assertEquals(departamentoId, resultado.getId());
//        assertEquals("TI", resultado.getNome());
//
//
//        // Verifica chamadas aos mocks
//        verify(departamentoRepository, times(1)).findById((long)departamentoId);
    }

}


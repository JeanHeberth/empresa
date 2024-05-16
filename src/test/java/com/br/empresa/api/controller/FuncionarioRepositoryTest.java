package com.br.empresa.api.controller;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.service.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class FuncionarioRepositoryTest {

    private FuncionarioService funcionarioService = mock(FuncionarioService.class);
    private FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

    @Test
    public void testBuscarTodosFuncionarios() {
        List<FuncionarioResponseDto> mockFuncionarios = Arrays.asList(
                new FuncionarioResponseDto(1L, "John Doe", "johndoe@example.com"),
                new FuncionarioResponseDto(2L, "Jane Smith", "janesmith@example.com")
        );

        when(funcionarioService.buscarFuncionarios()).thenReturn(mockFuncionarios);

        ResponseEntity<List<FuncionarioResponseDto>> responseEntity = funcionarioController.buscarTodosFuncionarios();

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(mockFuncionarios, responseEntity.getBody());

        verify(funcionarioService, times(1)).buscarFuncionarios();
    }

    @Test
    public void testBuscarFuncionarioPorId() {
        // Mock data
        Long funcionarioId = 1L;
        FuncionarioResponseDto mockFuncionario = new FuncionarioResponseDto(funcionarioId, "John Doe", "johndoe@example.com");

        when(funcionarioService.buscarFuncionarioPorId(funcionarioId)).thenReturn(mockFuncionario);

        ResponseEntity<FuncionarioResponseDto> responseEntity = funcionarioController.buscarFuncionarioPorId(funcionarioId);

        assertNotNull(responseEntity);
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals(mockFuncionario, responseEntity.getBody());

        verify(funcionarioService, times(1)).buscarFuncionarioPorId(funcionarioId);
    }

    @Test
    public void testExcluirFuncionario() {
        // Mock data
        Long funcionarioId = 1L;

        funcionarioController.apagarFuncionario(funcionarioId);

        // Verify that funcionarioService.excluirFuncionario() method was called with the correct ID
        verify(funcionarioService, times(1)).apagarFuncionario(funcionarioId);
    }

    @Test
    public void testCadastrarFuncionario() {
        // Mock data
        FuncionarioRequestDto mockRequestDto = new FuncionarioRequestDto("John Doe", "johndoe@example.com", "password123");
        FuncionarioResponseDto mockResponseDto = new FuncionarioResponseDto(1L, "John Doe", "johndoe@example.com");

        // Mock the behavior of funcionarioService.cadastrarFuncionario()
        when(funcionarioService.cadastrarFuncionario(mockRequestDto)).thenReturn(mockResponseDto);

        // Call the method to test
        ResponseEntity<FuncionarioResponseDto> responseEntity = funcionarioController.cadastrarFuncionario(mockRequestDto);

        // Verify that the ResponseEntity is not null
        assertNotNull(responseEntity);

        // Verify that the status code is OK (200)
        assertEquals(200, responseEntity.getStatusCodeValue());

        // Verify that the response body matches the mock data
        assertEquals(mockResponseDto, responseEntity.getBody());
    }

//    @Test
//    public void testAtualizarFuncionario() {
//        // Mock data
//        Long funcionarioId = 1L;
//        FuncionarioRequestDto mockRequestDto = new FuncionarioRequestDto("Jane Smith", "janesmith@example.com", "newpassword");
//        FuncionarioResponseDto mockResponseDto = new FuncionarioResponseDto(funcionarioId, "Jane Smith", "janesmith@example.com");
//
//        when(funcionarioService.atualizarFuncionario(funcionarioId, mockRequestDto)).thenReturn(mockResponseDto);
//
//        ResponseEntity<FuncionarioResponseDto> responseEntity = funcionarioController.atualizarFuncionario(funcionarioId, mockRequestDto);
//
//        assertNotNull(responseEntity);
//        assertEquals(200, responseEntity.getStatusCodeValue());
//        assertEquals(mockResponseDto, responseEntity.getBody());
//
//        verify(funcionarioService, times(1)).atualizarFuncionario(funcionarioId, mockRequestDto);
//    }
}


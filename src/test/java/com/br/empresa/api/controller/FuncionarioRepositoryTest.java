package com.br.empresa.api.controller;

import com.br.empresa.api.dto.FuncionarioRequestDto;
import com.br.empresa.api.dto.FuncionarioResponseDto;
import com.br.empresa.api.service.FuncionarioService;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FuncionarioRepositoryTest {


    private FuncionarioService funcionarioService = mock(FuncionarioService.class);
    private FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

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
}


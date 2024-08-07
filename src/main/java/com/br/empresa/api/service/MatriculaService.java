package com.br.empresa.api.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Random;

@Service
public class MatriculaService {

    public String gerarMatricula(LocalDate dataAdmissao, String cpf) {

        String anoAdmissao = String.valueOf(dataAdmissao.getYear());

        String ultimosNumerosCpf = cpf.substring(cpf.length() - 2);

        String letrasAleatorias = generateRandomLetters(2);


        return anoAdmissao + ultimosNumerosCpf + letrasAleatorias;

    }

    private String generateRandomLetters(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char randomLetter = (char) ('A' + random.nextInt(26));
            sb.append(randomLetter);
        }
        return sb.toString();
    }

}

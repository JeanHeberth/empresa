package com.br.empresa.api.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@SuppressWarnings("serial")
public class GenericDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}

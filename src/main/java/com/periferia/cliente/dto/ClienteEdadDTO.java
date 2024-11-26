package com.periferia.cliente.dto;

import lombok.Data;

@Data
public class ClienteEdadDTO {
    private String nombreCompleto;
    private int edad;

    public ClienteEdadDTO(String nombreCompleto, int edad) {
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
    }
}

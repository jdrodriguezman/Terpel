package com.periferia.cliente.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteDTO {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String correoElectronico;
    private LocalDate fechaNacimiento;

    public ClienteDTO(String nombreCompleto, String documentoIdentidad, String correoElectronico, LocalDate fechaNacimiento) {
        this.nombreCompleto = nombreCompleto;
        this.documentoIdentidad = documentoIdentidad;
        this.correoElectronico = correoElectronico;
        this.fechaNacimiento = fechaNacimiento;
    }
}

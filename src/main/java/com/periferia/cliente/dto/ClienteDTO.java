package com.periferia.cliente.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteDTO {
    private String nombreCompleto;
    private String documentoIdentidad;
    private String correoElectronico;
    private LocalDate fechaNacimiento;
}

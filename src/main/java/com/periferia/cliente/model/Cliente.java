package com.periferia.cliente.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCompleto;

    @Column(unique = true, nullable = false)
    private String documentoIdentidad;

    @Column(unique = true, nullable = false)
    private String correoElectronico;

    private LocalDate fechaNacimiento;
}

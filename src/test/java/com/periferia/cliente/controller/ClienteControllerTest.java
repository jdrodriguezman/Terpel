package com.periferia.cliente.controller;

import com.periferia.cliente.dto.ClienteDTO;
import com.periferia.cliente.dto.ClienteEdadDTO;
import com.periferia.cliente.dto.EstadisticasClientesDTO;
import com.periferia.cliente.model.Cliente;
import com.periferia.cliente.service.ClienteService;
import com.periferia.cliente.service.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteControllerTest {

    @InjectMocks
    private ClienteController clienteController;

    @Mock
    private ClienteService clienteService;

    @Mock
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("John Doe", "123456", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        Cliente cliente = Cliente.builder()
                .nombreCompleto("John Doe")
                .documentoIdentidad("123456")
                .correoElectronico("john.doe@example.com")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();
        when(clienteService.guardarCliente(clienteDTO)).thenReturn(cliente);

        ResponseEntity<Cliente> response = clienteController.guardarCliente(clienteDTO);

        assertNotNull(response);
        assertEquals(cliente, response.getBody());
        verify(clienteService, times(1)).guardarCliente(clienteDTO);
    }

    @Test
    void obtenerClientesOrdenados() {
        List<ClienteDTO> clientes = Arrays.asList(
                new ClienteDTO("Alice", "111", "alice@example.com", LocalDate.of(1980, 5, 20)),
                new ClienteDTO("Bob", "222", "bob@example.com", LocalDate.of(1992, 8, 15))
        );
        when(clienteService.obtenerClientesOrdenados()).thenReturn(clientes);

        List<ClienteDTO> result = clienteController.obtenerClientesOrdenados();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getNombreCompleto());
        verify(clienteService, times(1)).obtenerClientesOrdenados();
    }

    @Test
    void obtenerClientesPorEdad() {
        List<ClienteEdadDTO> clientesPorEdad = Arrays.asList(
                new ClienteEdadDTO("Alice", 43),
                new ClienteEdadDTO("Bob", 31)
        );
        when(clienteService.obtenerClientesPorEdad()).thenReturn(clientesPorEdad);

        List<ClienteEdadDTO> result = clienteController.obtenerClientesPorEdad();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.get(0).getNombreCompleto());
        verify(clienteService, times(1)).obtenerClientesPorEdad();
    }

    @Test
    void obtenerEstadisticasClientes() {
        EstadisticasClientesDTO estadisticas = new EstadisticasClientesDTO(10, 35.5);
        when(clienteService.obtenerEstadisticasClientes()).thenReturn(estadisticas);

        EstadisticasClientesDTO result = clienteController.obtenerEstadisticasClientes();

        assertNotNull(result);
        assertEquals(10, result.getCantidadClientes());
        assertEquals(35.5, result.getPromedioEdad());
        verify(clienteService, times(1)).obtenerEstadisticasClientes();
    }

    @Test
    void generateJWT() {
        String nombre = "John Doe";
        String correo = "john.doe@example.com";
        String expectedToken = "fake.jwt.token";
        when(jwtService.generarToken(nombre, correo)).thenReturn(expectedToken);

        String token = clienteController.generateJWT(nombre, correo);

        assertNotNull(token);
        assertEquals(expectedToken, token);
        verify(jwtService, times(1)).generarToken(nombre, correo);
    }

    @Test
    void obtenerEdad() {
        String correo = "john.doe@example.com";
        int expectedEdad = 33;
        when(clienteService.obtenerEdadPorCorreo(correo)).thenReturn(expectedEdad);

        int edad = clienteController.obtenerEdad(correo);

        assertEquals(expectedEdad, edad);
        verify(clienteService, times(1)).obtenerEdadPorCorreo(correo);
    }
}

package com.periferia.cliente.service;

import com.periferia.cliente.dto.ClienteDTO;
import com.periferia.cliente.dto.ClienteEdadDTO;
import com.periferia.cliente.dto.EstadisticasClientesDTO;
import com.periferia.cliente.model.Cliente;
import com.periferia.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarCliente() {
        ClienteDTO clienteDTO = new ClienteDTO("John Doe", "123456", "john.doe@example.com", LocalDate.of(1990, 1, 1));
        Cliente cliente = Cliente.builder()
                .nombreCompleto(clienteDTO.getNombreCompleto())
                .documentoIdentidad(clienteDTO.getDocumentoIdentidad())
                .correoElectronico(clienteDTO.getCorreoElectronico())
                .fechaNacimiento(clienteDTO.getFechaNacimiento())
                .build();

        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente result = clienteService.guardarCliente(clienteDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getNombreCompleto());
        assertEquals("123456", result.getDocumentoIdentidad());
        assertEquals("john.doe@example.com", result.getCorreoElectronico());
    }

    @Test
    void testObtenerClientesOrdenados() {
        Cliente cliente1 = Cliente.builder()
                .nombreCompleto("John Doe")
                .documentoIdentidad("123456")
                .correoElectronico("john.doe@example.com")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombreCompleto("Alice Smith")
                .documentoIdentidad("654321")
                .correoElectronico("alice.smith@example.com")
                .fechaNacimiento(LocalDate.of(1985, 5, 15))
                .build();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteDTO> clientes = clienteService.obtenerClientesOrdenados();

        assertEquals(2, clientes.size());
        assertEquals("Alice Smith", clientes.get(0).getNombreCompleto());
        assertEquals("John Doe", clientes.get(1).getNombreCompleto());
    }

    @Test
    void testObtenerClientesPorEdad() {
        Cliente cliente1 = Cliente.builder()
                .nombreCompleto("John Doe")
                .documentoIdentidad("123456")
                .correoElectronico("john.doe@example.com")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombreCompleto("Alice Smith")
                .documentoIdentidad("654321")
                .correoElectronico("alice.smith@example.com")
                .fechaNacimiento(LocalDate.of(1985, 5, 15))
                .build();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        List<ClienteEdadDTO> clientesPorEdad = clienteService.obtenerClientesPorEdad();

        assertEquals(2, clientesPorEdad.size());
        assertEquals("Alice Smith", clientesPorEdad.get(1).getNombreCompleto());
    }

    @Test
    void testObtenerEstadisticasClientes() {
        Cliente cliente1 = Cliente.builder()
                .nombreCompleto("John Doe")
                .documentoIdentidad("123456")
                .correoElectronico("john.doe@example.com")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        Cliente cliente2 = Cliente.builder()
                .nombreCompleto("Alice Smith")
                .documentoIdentidad("654321")
                .correoElectronico("alice.smith@example.com")
                .fechaNacimiento(LocalDate.of(1985, 5, 15))
                .build();

        when(clienteRepository.findAll()).thenReturn(Arrays.asList(cliente1, cliente2));

        EstadisticasClientesDTO estadisticas = clienteService.obtenerEstadisticasClientes();

        assertEquals(2, estadisticas.getCantidadClientes());
        assertTrue(estadisticas.getPromedioEdad() > 0);
    }

    @Test
    void testObtenerEdadPorCorreo() {
        Cliente cliente = Cliente.builder()
                .nombreCompleto("John Doe")
                .documentoIdentidad("123456")
                .correoElectronico("john.doe@example.com")
                .fechaNacimiento(LocalDate.of(1990, 1, 1))
                .build();

        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        int edad = clienteService.obtenerEdadPorCorreo("john.doe@example.com");

        assertTrue(edad > 0);
        assertEquals(LocalDate.now().getYear() - 1990, edad);
    }

    @Test
    void testObtenerEdadPorCorreoClienteNoEncontrado() {
        when(clienteRepository.findAll()).thenReturn(List.of());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            clienteService.obtenerEdadPorCorreo("john.doe@example.com");
        });

        assertEquals("Cliente no encontrado con el correo: john.doe@example.com", exception.getMessage());
    }
}

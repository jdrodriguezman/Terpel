package com.periferia.cliente.service;

import com.periferia.cliente.dto.ClienteDTO;
import com.periferia.cliente.dto.ClienteEdadDTO;
import com.periferia.cliente.dto.EstadisticasClientesDTO;
import com.periferia.cliente.model.Cliente;
import com.periferia.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente guardarCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setNombreCompleto(clienteDTO.getNombreCompleto());
        cliente.setDocumentoIdentidad(clienteDTO.getDocumentoIdentidad());
        cliente.setCorreoElectronico(clienteDTO.getCorreoElectronico());
        cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());
        return clienteRepository.save(cliente);
    }

    public List<ClienteDTO> obtenerClientesOrdenados() {
        return clienteRepository.findAll().stream()
                .sorted((c1, c2) -> c1.getNombreCompleto().compareToIgnoreCase(c2.getNombreCompleto()))
                .map(cliente -> new ClienteDTO(
                        cliente.getNombreCompleto(),
                        cliente.getDocumentoIdentidad(),
                        cliente.getCorreoElectronico(),
                        cliente.getFechaNacimiento()
                ))
                .collect(Collectors.toList());
    }

    public List<ClienteEdadDTO> obtenerClientesPorEdad() {
        return clienteRepository.findAll().stream()
                .map(cliente -> new ClienteEdadDTO(
                        cliente.getNombreCompleto(),
                        calcularEdad(cliente.getFechaNacimiento())
                ))
                .sorted((c1, c2) -> Integer.compare(c1.getEdad(), c2.getEdad()))
                .collect(Collectors.toList());
    }

    public EstadisticasClientesDTO obtenerEstadisticasClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        long cantidadClientes = clientes.size();
        double promedioEdad = clientes.stream()
                .mapToInt(cliente -> calcularEdad(cliente.getFechaNacimiento()))
                .average()
                .orElse(0.0);

        return new EstadisticasClientesDTO(cantidadClientes, promedioEdad);
    }

    private int calcularEdad(LocalDate fechaNacimiento) {
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    public int obtenerEdadPorCorreo(String correo) {
        Optional<Cliente> clienteOpt = clienteRepository.findAll().stream()
                .filter(cliente -> cliente.getCorreoElectronico().equals(correo))
                .findFirst();

        if (!clienteOpt.isPresent()) {
            throw new RuntimeException("Cliente no encontrado con el correo: " + correo);
        }

        Cliente cliente = clienteOpt.get();

        Function<LocalDate, Integer> calcularEdad = fechaNacimiento -> {
            LocalDate fechaActual = LocalDate.now();
            return Period.between(fechaNacimiento, fechaActual).getYears();
        };

        return calcularEdad.apply(cliente.getFechaNacimiento());
    }
}

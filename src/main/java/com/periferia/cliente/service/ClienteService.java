package com.periferia.cliente.service;

import com.periferia.cliente.dto.ClienteDTO;
import com.periferia.cliente.model.Cliente;
import com.periferia.cliente.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}

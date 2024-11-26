package com.periferia.cliente.controller;

import com.periferia.cliente.dto.ClienteDTO;
import com.periferia.cliente.model.Cliente;
import com.periferia.cliente.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.guardarCliente(clienteDTO);
        return ResponseEntity.ok(cliente);
    }
}

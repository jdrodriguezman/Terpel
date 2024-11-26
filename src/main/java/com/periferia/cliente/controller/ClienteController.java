package com.periferia.cliente.controller;

import com.periferia.cliente.dto.ClienteDTO;
import com.periferia.cliente.dto.ClienteEdadDTO;
import com.periferia.cliente.dto.EstadisticasClientesDTO;
import com.periferia.cliente.model.Cliente;
import com.periferia.cliente.service.ClienteService;
import com.periferia.cliente.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;
    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<Cliente> guardarCliente(@RequestBody ClienteDTO clienteDTO) {
        Cliente cliente = clienteService.guardarCliente(clienteDTO);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("/ordenados")
    public List<ClienteDTO> obtenerClientesOrdenados() {
        return clienteService.obtenerClientesOrdenados();
    }

    @GetMapping("/por-edad")
    public List<ClienteEdadDTO> obtenerClientesPorEdad() {
        return clienteService.obtenerClientesPorEdad();
    }

    @GetMapping("/estadisticas")
    public EstadisticasClientesDTO obtenerEstadisticasClientes() {
        return clienteService.obtenerEstadisticasClientes();
    }

    @GetMapping("/generate-jwt")
    public String generateJWT(@RequestParam String nombre, @RequestParam String correo) {
        return jwtService.generarToken(nombre, correo);
    }

    @GetMapping("/edad")
    public int obtenerEdad(@RequestParam String correo) {
        return clienteService.obtenerEdadPorCorreo(correo);
    }
}

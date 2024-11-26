package com.periferia.cliente.service;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JwtServiceTest {

    @Test
    public void testGenerarToken() {
        JwtService jwtService = new JwtService();
        String nombre = "John Doe";
        String correo = "john.doe@example.com";

        String token = jwtService.generarToken(nombre, correo);

        assertNotNull(token, "El token no debe ser nulo");

        DecodedJWT decodedJWT = JWT.decode(token);

        assertEquals("JWT", decodedJWT.getSubject(), "El subject del token es incorrecto");
        assertEquals(nombre, decodedJWT.getClaim("nombre").asString(), "El nombre en el token es incorrecto");
        assertEquals(correo, decodedJWT.getClaim("correo").asString(), "El correo en el token es incorrecto");

        assertNotNull(decodedJWT.getIssuedAt(), "El token debe tener una fecha de emisión");
        assertNotNull(decodedJWT.getExpiresAt(), "El token debe tener una fecha de expiración");
        assertTrue(decodedJWT.getExpiresAt().after(decodedJWT.getIssuedAt()),
                "La fecha de expiración debe ser posterior a la fecha de emisión");
    }
}

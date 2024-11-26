package com.periferia.cliente.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JwtService {

    public static final String SECRET_KEY = "ejercicioentrevista";
    public static final long EXPIRATION_TIME = 86400000;

    public String generarToken(String nombre, String correo) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject("JWT")
                .withClaim("nombre", nombre)
                .withClaim("correo", correo)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(algorithm);
    }
}

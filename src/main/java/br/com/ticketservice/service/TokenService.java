package br.com.ticketservice.service;

import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.exception.BadRequestException;
import br.com.ticketservice.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public String generateToken(User user) {
        LocalDateTime now = LocalDateTime.now().plusHours(2);
        long expiry = 2l;

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("ticket-service")
                .issuedAt(now.toInstant(ZoneOffset.of("-03:00")))
                .expiresAt(now.plusHours(expiry).toInstant(ZoneOffset.of("-03:00")))
                .subject(user.getUsername())
                .build();

        return encoder.encode(
                        JwtEncoderParameters.from(claims))
                .getTokenValue();
    }

    public void validateToken(String token) {
        Jwt jwt = decoder.decode(token);
        if (Objects.requireNonNull(jwt.getExpiresAt()).isBefore(Instant.now())) {
            throw new UnauthorizedException();
        }
    }

}
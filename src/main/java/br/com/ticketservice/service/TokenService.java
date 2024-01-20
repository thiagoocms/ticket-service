package br.com.ticketservice.service;

import br.com.ticketservice.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

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

    public String validateToken(String token) {
        return  decoder.decode(token).getTokenValue();
    }

}
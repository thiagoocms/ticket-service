package br.com.ticketservice.service.impl;

import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.dto.user.UserAuthenticatedDTO;
import br.com.ticketservice.dto.user.UserLoginDTO;
import br.com.ticketservice.exception.BadRequestException;
import br.com.ticketservice.service.AbstractService;
import br.com.ticketservice.service.AuthService;
import br.com.ticketservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends AbstractService implements AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager manager;

    @Override
    public UserAuthenticatedDTO auth(UserLoginDTO userLoginDTO) throws Throwable {
        String[] decrypt = new String(Base64.getDecoder().decode(userLoginDTO.getAuth())).split(":");
        if (decrypt.length < 2) {
            throw new BadRequestException();
        }
        var login = decrypt[0];
        var password = decrypt[1];
        if ((login == null || login.isEmpty()) || (password == null || password.isEmpty())) {
            throw badRequestException("error.user.login.required");
        }
        var auth = new UsernamePasswordAuthenticationToken(decrypt[0], decrypt[1]);
        Authentication authentication = manager.authenticate(auth);
        String token = tokenService.generateToken((User) authentication.getPrincipal());
        return new UserAuthenticatedDTO(token);
    }

}
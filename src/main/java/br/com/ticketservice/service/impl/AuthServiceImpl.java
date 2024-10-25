package br.com.ticketservice.service.impl;

import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.dto.user.UserAuthenticatedDTO;
import br.com.ticketservice.dto.user.UserLoginDTO;
import br.com.ticketservice.service.AbstractService;
import br.com.ticketservice.service.AuthService;
import br.com.ticketservice.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl extends AbstractService implements AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager manager;

    @Override
    public UserAuthenticatedDTO auth(UserLoginDTO userLoginDTO) throws Throwable {
        if ((userLoginDTO.getLogin() == null || userLoginDTO.getLogin().isEmpty()) || (userLoginDTO.getPassword() == null || userLoginDTO.getPassword().isEmpty())) {
            throw badRequestException("error.user.login.required");
        }
        var auth = new UsernamePasswordAuthenticationToken(userLoginDTO.getLogin(), userLoginDTO.getPassword());
        var authentication = manager.authenticate(auth);
        String token = tokenService.generateToken((User) authentication.getPrincipal());
        return new UserAuthenticatedDTO(token);
    }

}
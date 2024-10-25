package br.com.ticketservice.service;

import br.com.ticketservice.dto.user.UserAuthenticatedDTO;
import br.com.ticketservice.dto.user.UserLoginDTO;

public interface AuthService {

    UserAuthenticatedDTO auth(UserLoginDTO userLoginDTO) throws Throwable;
}
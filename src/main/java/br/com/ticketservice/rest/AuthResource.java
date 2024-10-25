package br.com.ticketservice.rest;

import br.com.ticketservice.constants.AppConstants;
import br.com.ticketservice.dto.user.UserAuthenticatedDTO;
import br.com.ticketservice.dto.user.UserLoginDTO;
import br.com.ticketservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = AppConstants.PATH + AppConstants.API + AppConstants.V1 + "/auth")
@RequiredArgsConstructor
public class AuthResource {

    private final AuthService authService;

    @PostMapping
    public ResponseEntity<UserAuthenticatedDTO> auth(@RequestBody UserLoginDTO dto) throws Throwable {

        UserAuthenticatedDTO tokenDTO = authService.auth(dto);

        return ResponseEntity
                .ok()
                .body(tokenDTO);
    }
}

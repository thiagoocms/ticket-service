package br.com.ticketservice.rest;

import br.com.ticketservice.constants.AppConstants;
import br.com.ticketservice.dto.Response;
import br.com.ticketservice.dto.user.UserAuthenticatedDTO;
import br.com.ticketservice.dto.user.UserDTO;
import br.com.ticketservice.dto.user.UserLoginDTO;
import br.com.ticketservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AppConstants.PATH + AppConstants.API + AppConstants.V1 + "/users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("/by-list")
    public ResponseEntity<Response<List<UserDTO>>> createOrUpdateByList(@RequestBody List<UserDTO> dtoList) throws Throwable {

        List<UserDTO> list = userService.createOrUpdateByList(dtoList);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, list));
    }

    @PostMapping
    public  ResponseEntity<Response<UserDTO>> create(@RequestBody UserDTO dto) throws Throwable {

        dto = userService.create(dto);

        return ResponseEntity
                .created(URI.create("/users"))
                .body(new Response<>(HttpStatus.CREATED, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> update(@PathVariable Long id, @RequestBody UserDTO dto) throws Throwable {

        dto = userService.update(id, dto);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, dto));
    }

    @GetMapping
    public ResponseEntity<Response<List<UserDTO>>> findByAll(Pageable pageable) {

        Page<UserDTO> page = userService.findByAll(pageable);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<UserDTO>> findById(@PathVariable Long id) throws Throwable {

        UserDTO userDTO = userService.findById(id);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, userDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Throwable {

        userService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
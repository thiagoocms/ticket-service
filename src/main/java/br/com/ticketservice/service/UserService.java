package br.com.ticketservice.service;

import br.com.ticketservice.dto.user.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    List<UserDTO> createOrUpdateByList(List<UserDTO> dtoList) throws Throwable;

    UserDTO create(UserDTO dto) throws Throwable;

    UserDTO update(Long id, UserDTO dto) throws Throwable;

    UserDTO findById(Long id) throws Throwable;

    Page<UserDTO> findByAll(Pageable pageable);

    void delete(Long id) throws Throwable;

    UserDTO findByLogin(String username) throws Throwable;
}
package br.com.ticketservice.service;

import br.com.ticketservice.dto.ticket.TicketDTO;
import br.com.ticketservice.dto.ticket.TicketFilterDTO;
import br.com.ticketservice.dto.ticket.TicketSimpleDTO;
import br.com.ticketservice.enumerated.TicketStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketService {

    List<TicketSimpleDTO> createOrUpdateByList(List<TicketSimpleDTO> dtoList) throws Throwable;

    TicketSimpleDTO create(TicketSimpleDTO dto) throws Throwable;

    TicketSimpleDTO update(Long id, TicketSimpleDTO dto) throws Throwable;

    TicketDTO findById(Long id) throws Throwable;

    Page<TicketSimpleDTO> findByAll(Pageable pageable, TicketFilterDTO filter);

    void delete(Long id) throws Throwable;

    List<TicketStatusEnum> findStatus();
}
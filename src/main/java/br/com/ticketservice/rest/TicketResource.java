package br.com.ticketservice.rest;

import br.com.ticketservice.constants.AppConstants;
import br.com.ticketservice.dto.ticket.TicketDTO;
import br.com.ticketservice.dto.ticket.TicketSimpleDTO;
import br.com.ticketservice.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AppConstants.PATH + AppConstants.API + AppConstants.V1 + "/tickets")
@RequiredArgsConstructor
public class TicketResource {

    private final TicketService ticketService;

    @PostMapping("/by-list")
    public ResponseEntity<List<TicketSimpleDTO>> createOrUpdateByList(@RequestBody List<TicketSimpleDTO> dtoList) throws Throwable {

        List<TicketSimpleDTO> list = ticketService.createOrUpdateByList(dtoList);

        return ResponseEntity
                .ok()
                .body(list);
    }

    @PostMapping
    public ResponseEntity<TicketSimpleDTO> create(@RequestBody TicketSimpleDTO dto) throws Throwable {

        dto = ticketService.create(dto);

        return ResponseEntity
                .created(URI.create("/categories"))
                .body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketSimpleDTO> update(@PathVariable Long id, @RequestBody TicketSimpleDTO dto) throws Throwable {

        dto = ticketService.update(id, dto);

        return ResponseEntity
                .ok()
                .body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<TicketSimpleDTO>> findByAll(Pageable pageable) {

        Page<TicketSimpleDTO> page = ticketService.findByAll(pageable);

        return ResponseEntity
                .ok()
                .body(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> findById(@PathVariable Long id) throws Throwable {

        TicketDTO userDTO = ticketService.findById(id);

        return ResponseEntity
                .ok()
                .body(userDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Throwable {

        ticketService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
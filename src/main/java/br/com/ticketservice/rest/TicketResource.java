package br.com.ticketservice.rest;

import br.com.ticketservice.constants.AppConstants;
import br.com.ticketservice.dto.Response;
import br.com.ticketservice.dto.ticket.TicketDTO;
import br.com.ticketservice.dto.ticket.TicketSimpleDTO;
import br.com.ticketservice.enumerated.TicketStatusEnum;
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
    public ResponseEntity<Response<List<TicketSimpleDTO>>> createOrUpdateByList(@RequestBody List<TicketSimpleDTO> dtoList) throws Throwable {

        List<TicketSimpleDTO> list = ticketService.createOrUpdateByList(dtoList);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, list));
    }

    @PostMapping
    public ResponseEntity<Response<TicketSimpleDTO>> create(@RequestBody TicketSimpleDTO dto) throws Throwable {

        dto = ticketService.create(dto);

        return ResponseEntity
                .created(URI.create("/tickets"))
                .body(new Response<>(HttpStatus.CREATED, dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response<TicketSimpleDTO>> update(@PathVariable Long id, @RequestBody TicketSimpleDTO dto) throws Throwable {

        dto = ticketService.update(id, dto);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, dto));
    }

    @GetMapping
    public ResponseEntity<Response<List<TicketSimpleDTO>>> findByAll(Pageable pageable) {

        Page<TicketSimpleDTO> page = ticketService.findByAll(pageable);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, page));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<TicketDTO>> findById(@PathVariable Long id) throws Throwable {

        TicketDTO dto = ticketService.findById(id);

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Throwable {

        ticketService.delete(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/status")
    public ResponseEntity<Response<List<TicketStatusEnum>>> findStatus() {

        List<TicketStatusEnum> list = ticketService.findStatus();

        return ResponseEntity
                .ok()
                .body(new Response<>(HttpStatus.OK, list));
    }
}
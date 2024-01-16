package br.com.ticketservice.service.impl;

import br.com.ticketservice.domain.ticket.Ticket;
import br.com.ticketservice.dto.ticket.TicketDTO;
import br.com.ticketservice.dto.ticket.TicketSimpleDTO;
import br.com.ticketservice.repository.TicketRepository;
import br.com.ticketservice.service.AbstractService;
import br.com.ticketservice.service.TicketService;
import br.com.ticketservice.validation.TicketValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl extends AbstractService implements TicketService {

    private final TicketRepository ticketRepository;
    private final TicketValidation ticketValidation;

    @Override
    public List<TicketSimpleDTO> createOrUpdateByList(List<TicketSimpleDTO> dtoList) throws Throwable {
        List<TicketSimpleDTO> userArrayList = new ArrayList<>();
        for (TicketSimpleDTO dto : dtoList) {
            if (Objects.isNull(dto.getId())) {
                userArrayList.add(this.create(dto));
            } else {
                userArrayList.add(this.update(dto.getId(), dto));
            }
        }
        return userArrayList;
    }

    @Override
    public TicketSimpleDTO create(TicketSimpleDTO dto) throws Throwable {

        Ticket entity = modelMapper.map(dto, Ticket.class);
        ticketValidation.checkOwnerFieldsToCreate(entity);
        ticketValidation.checkMandatoryFields(entity);
        ticketValidation.checkRelations(entity);
        modelMapper.map(ticketRepository.save(entity), dto);

        return dto;
    }

    @Override
    public TicketSimpleDTO update(Long id, TicketSimpleDTO dto) throws Throwable {

        Ticket entity = modelMapper.map(dto, Ticket.class);
        ticketValidation.checkUpdateConsistence(id, entity);
        ticketValidation.checkMandatoryFields(entity);
        ticketValidation.checkRelations(entity);
        modelMapper.map(ticketRepository.save(entity), dto);

        return dto;
    }

    @Override
    public TicketDTO findById(Long id) throws Throwable {

        Ticket entity = ticketValidation.checkExistTicket(id);

        return modelMapper.map(entity, TicketDTO.class);
    }

    @Override
    public Page<TicketSimpleDTO> findByAll(Pageable pageable) {

        Page<Ticket> page = this.ticketRepository.findAllByDeletedIsFalse(pageable);
        return page.map(item -> modelMapper.map(item, TicketSimpleDTO.class));

    }

    @Override
    public void delete(Long id) throws Throwable {

        Ticket entity = ticketValidation.checkExistTicket(id);
        entity.setDeleted(Boolean.TRUE);
        ticketRepository.save(entity);

    }
}
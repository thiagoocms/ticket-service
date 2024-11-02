package br.com.ticketservice.repository;

import br.com.ticketservice.domain.ticket.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long>, JpaSpecificationExecutor<Ticket> {

    Ticket findFirstByIdAndDeletedIsFalse(Long id);

    Page<Ticket> findAllByDeletedIsFalse(Pageable pageable);
}

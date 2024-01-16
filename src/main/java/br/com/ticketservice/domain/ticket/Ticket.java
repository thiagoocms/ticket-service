package br.com.ticketservice.domain.ticket;

import br.com.ticketservice.domain.AbstractAuditingEntity;
import br.com.ticketservice.domain.category.Category;
import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.enumerated.TicketStatusEnum;
import jakarta.persistence.*;
import org.joda.time.DateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tb_tickets")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Ticket extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", length = 100)
    private String title;

    @Column(name = "description", length = 500)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private User employee;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status")
    private TicketStatusEnum status;

    @Column(name = "waiting_date")
    private DateTime waitingDate;

    @Column(name = "finished_date")
    private DateTime finishedDate;
}

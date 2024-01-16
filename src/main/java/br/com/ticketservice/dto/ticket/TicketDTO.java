package br.com.ticketservice.dto.ticket;

import br.com.ticketservice.dto.category.CategoryDTO;
import br.com.ticketservice.dto.user.UserDTO;
import br.com.ticketservice.enumerated.TicketStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TicketDTO implements Serializable {

    private Long id;

    private String title;

    private String description;

    private UserDTO user;

    private CategoryDTO category;

    private UserDTO employee;

    private TicketStatusEnum status;

    private Long waitingDate;

    private Long finishedDate;
}

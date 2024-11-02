package br.com.ticketservice.dto.ticket;

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
public class TicketFilterDTO implements Serializable {
    private Long userId;
}

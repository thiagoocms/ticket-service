package br.com.ticketservice.dto.user;

import br.com.ticketservice.enumerated.UserDocumentTypeEnum;
import br.com.ticketservice.enumerated.UserProfileEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDTO implements Serializable {
    private Long id;
    private String name;
    private String documentNumber;
    private UserDocumentTypeEnum documentType;
    private String login;
    private String password;
    private UserProfileEnum profile;
}

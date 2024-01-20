package br.com.ticketservice.domain.user;

import br.com.ticketservice.domain.AbstractAuditingEntity;
import br.com.ticketservice.enumerated.UserDocumentTypeEnum;
import br.com.ticketservice.enumerated.UserProfileEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

@Entity
@Table(name = "tb_users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User extends AbstractAuditingEntity implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Column(name = "document_number", length = 50, nullable = false)
    private String documentNumber;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "document_type", nullable = false)
    private UserDocumentTypeEnum documentType;

    @Column(name = "login", length = 255, nullable = false)
    private String login;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "profile", nullable = false)
    private UserProfileEnum profile;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

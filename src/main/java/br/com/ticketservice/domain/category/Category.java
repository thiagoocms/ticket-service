package br.com.ticketservice.domain.category;

import br.com.ticketservice.domain.AbstractAuditingEntity;
import br.com.ticketservice.domain.user.User;
import br.com.ticketservice.enumerated.CategoryPriorityEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "tb_categories")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 255)
    private String name;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "priority")
    private CategoryPriorityEnum priority;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private User employee;
}

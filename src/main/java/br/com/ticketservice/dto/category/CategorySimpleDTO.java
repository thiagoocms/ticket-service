package br.com.ticketservice.dto.category;

import br.com.ticketservice.enumerated.CategoryPriorityEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategorySimpleDTO implements Serializable {

    private Long id;
    private String name;
    private CategoryPriorityEnum priority;
    private Long employeeId;
}

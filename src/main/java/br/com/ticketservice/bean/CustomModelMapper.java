package br.com.ticketservice.bean;

import lombok.EqualsAndHashCode;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@EqualsAndHashCode(callSuper=false)
public class CustomModelMapper extends ModelMapper {

    @Autowired
    public CustomModelMapper() {
        this.getConfiguration().setMatchingStrategy(MatchingStrategies.STANDARD);
    }
}
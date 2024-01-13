package br.com.ticketservice.config;

import br.com.ticketservice.bean.CustomModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public CustomModelMapper modelMapper() {
        CustomModelMapper modelMapper = new CustomModelMapper();
        return modelMapper;
    }
}

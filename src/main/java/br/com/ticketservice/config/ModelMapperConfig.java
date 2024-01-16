package br.com.ticketservice.config;

import br.com.ticketservice.bean.CustomModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Configuration
public class ModelMapperConfig {

    @Bean
    public CustomModelMapper modelMapper() {
        CustomModelMapper modelMapper = new CustomModelMapper();

        TypeMap<LocalDateTime, Long> localDateTimeToTimestampTypeMap = modelMapper.createTypeMap(LocalDateTime.class, Long.class);
        localDateTimeToTimestampTypeMap.setConverter(context -> {
            LocalDateTime source = context.getSource();
            if (source == null) {
                return null;
            }
            return source.atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
        });

        TypeMap<Long, LocalDateTime> timestampToLocalDateTimeTypeMap = modelMapper.createTypeMap(Long.class, LocalDateTime.class);
        timestampToLocalDateTimeTypeMap.setConverter(context -> {
            Long source = context.getSource();
            if (source == null) {
                return null;
            }
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(source), ZoneOffset.UTC);
        });

        return modelMapper;
    }
}

package br.com.ticketservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDetailsDTO {
    private Long timestamp;
    private int status;
    private String message;
    private String details;

}

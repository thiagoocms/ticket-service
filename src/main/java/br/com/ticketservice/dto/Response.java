package br.com.ticketservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {
    private int status;
    private Object data;
    private Object filter;
    private Integer totalPages = null;
    private Long totalElements = null;


    public Response(HttpStatus status, T data) {
        this.status = status.value();
        this.data = data;
    }

    public Response(HttpStatus status, T data, Integer totalPages, Long totalElements) {
        this.status = status.value();
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    public Response(HttpStatus status, Page data) {
        this.status = status.value();
        this.data = data.toList();
        this.totalPages = data.getTotalPages();
        this.totalElements = data.getTotalElements();
    }

    public Response(HttpStatus status, List data) {
        this.status = status.value();
        this.data = data;
        this.totalPages = 1;
        this.totalElements = (long) data.size();
    }
}

package br.com.ticketservice.config;

import br.com.ticketservice.dto.ErrorDetailsDTO;
import br.com.ticketservice.exception.UnauthorizedException;
import br.com.ticketservice.service.TokenService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
           if (token != null) {
               tokenService.validateToken(token);
           }
            filterChain.doFilter(request, response);
        } catch (UnauthorizedException e) {
            ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli(), HttpStatus.UNAUTHORIZED.value(), e.getMessage(), null);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().write(new ObjectMapper().writeValueAsString(errorDetails));
        }
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
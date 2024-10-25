package br.com.ticketservice.exception;

import br.com.ticketservice.dto.ErrorDetailsDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetailsDTO> handleResourceNotFoundException(ResourceNotFoundException exception,
                                                                           WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(getTime(), HttpStatus.NOT_FOUND.value(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetailsDTO> handleBadRequestException(BadRequestException exception,
                                                                     WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(getTime(), HttpStatus.BAD_REQUEST.value(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorDetailsDTO> handleUnauthorizedExceptionException(UnauthorizedException exception,
                                                                                WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(getTime(), HttpStatus.UNAUTHORIZED.value(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler({InternalAuthenticationServiceException.class, BadCredentialsException.class})
    public ResponseEntity<ErrorDetailsDTO> handleForbiddenExceptionException(Exception exception,
                                                                             WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(getTime(), HttpStatus.FORBIDDEN.value(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetailsDTO> handleExceptionException(Exception exception, WebRequest webRequest) {
        ErrorDetailsDTO errorDetails = new ErrorDetailsDTO(getTime(), HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage(),
                webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private Long getTime() {
        return LocalDateTime.now().atZone(ZoneOffset.UTC).toInstant().toEpochMilli();
    }
}

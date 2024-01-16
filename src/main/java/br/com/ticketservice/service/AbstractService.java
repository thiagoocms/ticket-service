package br.com.ticketservice.service;


import br.com.ticketservice.exception.BadRequestException;
import br.com.ticketservice.exception.ConflictException;
import br.com.ticketservice.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;

import java.io.IOException;

@Service
 abstract public class AbstractService {

	//**********************************************************************************
	// PUBLIC ATTRIBUTES
	//**********************************************************************************

    public final Logger log = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public ObjectMapper objectMapper;

	@Autowired
	public MessageService messageService;

    //**********************************************************************************
 	// PUBLIC FUNCTIONS
 	//**********************************************************************************

 	public Throwable throwsException(String key) throws IOException {
		RestClientException ex = new RestClientException(key);
		ex.printStackTrace();
		throw ex;
 	}

	public Throwable conflictException(String key, String... args) throws IOException {
		ConflictException ex = new ConflictException(buildMessage(key,args));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable conflictException(String key) throws IOException {
		ConflictException ex = new ConflictException(buildMessage(key));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable badRequestException(String key, String... args) throws IOException {
		BadRequestException ex = new BadRequestException(buildMessage(key,args));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable badRequestException(String key) throws IOException {
		BadRequestException ex = new BadRequestException(buildMessage(key));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable resourceNotFoundException(String key, String... args) throws IOException {
		ResourceNotFoundException ex = new ResourceNotFoundException(buildMessage(key, args));
		ex.printStackTrace();
		throw ex;
	}

	public Throwable resourceNotFoundException(String key) throws IOException {
		ResourceNotFoundException ex = new ResourceNotFoundException(buildMessage(key));
		ex.printStackTrace();
		throw ex;
	}

	private String buildMessage(String key, String... args) {
		String message = messageService.getMessage(key, args);
		return message;
	}

	private String buildMessage(String key) {
		String message = messageService.getMessage(key);
		return message;
	}

}

package br.com.ticketservice.service;


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
public class AbstractService {

	//**********************************************************************************
	// PUBLIC ATTRIBUTES
	//**********************************************************************************

    public final Logger log = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	public ModelMapper modelMapper;

	@Autowired
	public ObjectMapper objectMapper;

    //**********************************************************************************
 	// PUBLIC FUNCTIONS
 	//**********************************************************************************

 	public Throwable throwsException(String key) throws IOException {
		RestClientException ex = new RestClientException(key);
		ex.printStackTrace();
		throw ex;
 	}

}

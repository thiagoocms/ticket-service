package br.com.ticketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.Locale;


@Service
public class MessageService {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String key, String... args) throws NoSuchMessageException {
		Locale locale = LocaleContextHolder.getLocale();
		String message = messageSource.getMessage(key, args, locale);
		return message;
	}

	public String getMessage(String key) throws NoSuchMessageException {
		return getMessage(key, "");
	}
}

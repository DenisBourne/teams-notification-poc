package com.zinkworks.teamsnotificationpoc.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class MainExceptionHandler {

	private ExceptionDetails logAndRespond(final Exception ex) {
		log.error(ex.getLocalizedMessage(), ex);
		return ExceptionDetails.builder()
				.title(ex.getLocalizedMessage())
				.exception(ex.toString())
				.build();
	}

	@ExceptionHandler(InternalServerError.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	ExceptionDetails handleException(final Exception ex) {
		return logAndRespond(ex);
	}

	@ExceptionHandler(InvalidRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	ExceptionDetails handleBadRequest(final Exception ex) {
		return logAndRespond(ex);
	}
}

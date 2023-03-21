package com.river.places.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ServiceException.class})
    public ResponseEntity<Object> handleServiceException(final ServiceException ex, final WebRequest request) {
	log.info(ex.getClass().getName());
	final ApiError apiError = new ApiError(ex.getCustomErrorCode());
	return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 500
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAllOtherUnknownException(final Exception ex, final WebRequest request) {
	log.info(ex.getClass().getName());
	log.error("Unknown Server Error : {}", ex);
	final ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR,
					       ex.getLocalizedMessage(),
					       "unknown error");
	return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 400
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
								  final HttpHeaders headers, final HttpStatus status,
								  final WebRequest request) {
	log.info(ex.getClass().getName());
	final List<String> errors = new ArrayList<>();
	for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
	    errors.add(error.getField() + ": " + error.getDefaultMessage());
	}
	for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	    errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	}
	final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException ex, final HttpHeaders headers,
							 final HttpStatus status, final WebRequest request) {
	log.info(ex.getClass().getName());
	//
	final List<String> errors = new ArrayList<>();
	for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
	    errors.add(error.getField() + ": " + error.getDefaultMessage());
	}
	for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	    errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
	}
	final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
	return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	log.info(ex.getClass().getName());
	final String error = ex.getParameterName() + " parameter is missing";
	final ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
	return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 404
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex,
								   final HttpHeaders headers, final HttpStatus status,
								   final WebRequest request) {
	log.info(ex.getClass().getName());
	final String error = "No handler is found for " + ex.getHttpMethod() + " " + ex.getRequestURL();
	final ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
	return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    // 405
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
	log.info(ex.getClass().getName());
	final StringBuilder builder = new StringBuilder();
	builder.append(ex.getMethod());
	builder.append(" method is not supported for this request. Supported methods are ");
	ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

	final ApiError apiError = new ApiError(HttpStatus.METHOD_NOT_ALLOWED,
					       ex.getLocalizedMessage(),
					       builder.toString());
	return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }

}
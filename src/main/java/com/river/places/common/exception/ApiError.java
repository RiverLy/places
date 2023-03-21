package com.river.places.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiError {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiError(final HttpStatus status, final String message, final String error) {
	super();
	this.status = status;
	this.message = message;
	errors = Arrays.asList(error);
    }

    public ApiError(final CustomErrorCode customErrorCode) {
	super();
	this.status = customErrorCode.getHttpStatus();
	this.message = customErrorCode.getMessage();
    }


}

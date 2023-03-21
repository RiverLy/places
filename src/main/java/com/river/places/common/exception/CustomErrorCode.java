package com.river.places.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum CustomErrorCode {

    EXTERNAL_API_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "External API call has failed. Try again later please."),
    NO_RESULT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "No results found.");

    private final HttpStatus httpStatus;
    private final String message;

}

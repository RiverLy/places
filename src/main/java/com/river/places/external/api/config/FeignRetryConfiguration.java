package com.river.places.external.api.config;

import feign.RetryableException;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.lang.String.format;

@Configuration
public class FeignRetryConfiguration {

    @Bean
    public Retryer retryer() {
	return new Retryer.Default(1000, 2000, 3);
    }

    @Bean
    public ErrorDecoder decoder() {
	return (methodKey, response) -> {
	    if (response.status() >= 500) {
		return new RetryableException(
			response.status(),
			format("%s request failed. Retry. - status: %s, headers: %s",
			       methodKey, response.status(), response.request().headers())
			, response.request().httpMethod()
			, null
			, response.request());
	    }

	    return new IllegalStateException(
		    format("%s request failed. - status: %s, headers: %s", methodKey, response.status(),
			   response.headers()));
	};
    }
}

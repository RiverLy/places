package com.river.places.external.api.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class NaverApiClientConfiguration {

    @Value("${api.naver.client-id}")
    private String clientId;

    @Value("${api.naver.client-secret}")
    private String clientSecret;

    @Bean
    public RequestInterceptor requestInterceptor() {
	return template -> {
	    template.header("X-Naver-Client-Id", clientId);
	    template.header("X-Naver-Client-Secret", clientSecret);
	};
    }
}

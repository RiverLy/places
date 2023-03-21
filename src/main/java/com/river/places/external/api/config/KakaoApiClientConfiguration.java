package com.river.places.external.api.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class KakaoApiClientConfiguration {

    @Value("${api.kakao.key}")
    private String apiKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
	return template -> {
	    template.header("Authorization", "KakaoAK " + apiKey);
	};
    }
}

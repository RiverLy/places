package com.river.places.external.api;

import com.river.places.external.api.config.KakaoApiClientConfiguration;
import com.river.places.search.external.model.response.KakaoSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakaoApiClient", url = "${api.kakao.url}", configuration = KakaoApiClientConfiguration.class)
public interface KakaoApiClient {
    @GetMapping("/v2/local/search/keyword.json")
    KakaoSearchResponse searchByKeyword(
	    @RequestParam("query") String query,
	    @RequestParam("page") Integer page,
	    @RequestParam("size") Integer size
    );

}

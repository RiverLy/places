package com.river.places.external.api;

import com.river.places.external.api.config.NaverApiClientConfiguration;
import com.river.places.search.external.model.response.NaverSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverApiClient", url = "${api.naver.url}", configuration = NaverApiClientConfiguration.class)
public interface NaverApiClient {
    @GetMapping("/v1/search/local.json")
    NaverSearchResponse searchByKeyword(@RequestParam("query") String query, @RequestParam("display") int display);
}


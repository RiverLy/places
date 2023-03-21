package com.river.places.search.external.service.implementation;

import com.river.places.external.api.KakaoApiClient;
import com.river.places.search.external.ExternalServiceCode;
import com.river.places.search.external.model.KakaoPlace;
import com.river.places.search.external.model.response.KakaoSearchResponse;
import com.river.places.search.external.service.ExternalPlaceSearchApiService;
import com.river.places.search.model.Place;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class KakaoLocalPlaceSearchApiService implements ExternalPlaceSearchApiService {

    private final KakaoApiClient kakaoApiClient;

    @Override
    public List<Place> searchByKeyword(String keyword, int page, int size) {
	try {
	    KakaoSearchResponse kakaoSearchResponse = kakaoApiClient.searchByKeyword(keyword, page, size);
	    return kakaoSearchResponse.getDocuments()
				      .stream()
				      .map(kakaoPlace -> toPlace(kakaoPlace))
				      .distinct()
				      .collect(Collectors.toList());
	} catch (Exception e) {
	    log.error("#####KakaoLocalPlaceSearchApiService - searchByKeyword : {}", e.getMessage());
	    return Collections.emptyList();
	}
    }

    private Place toPlace(KakaoPlace kakaoPlace) {
	return Place.builder().name(kakaoPlace.getPlaceName())
		    .address(kakaoPlace.getAddressName())
		    .roadAddress(kakaoPlace.getRoadAddressName())
		    .categoryName(kakaoPlace.getCategoryName())
		    .phone(kakaoPlace.getPhone())
		    .xCoordinate(kakaoPlace.getX())
		    .yCoordinate(kakaoPlace.getY())
		    .externalService(ExternalServiceCode.KAKAO)
		    .build();
    }
}



package com.river.places.external.search.service.implementation;

import com.river.places.common.api.NaverApiClient;
import com.river.places.external.search.service.ExternalPlaceSearchApiService;
import com.river.places.external.search.enums.ExternalServiceCode;
import com.river.places.external.search.model.NaverPlace;
import com.river.places.external.search.model.response.NaverSearchResponse;
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
public class NaverLocalPlaceSearchApiService implements ExternalPlaceSearchApiService {

    private final NaverApiClient naverApiClient;

    @Override
    public List<Place> searchByKeyword(String keyword, int page, int size) {
	try {
	    NaverSearchResponse naverSearchResponse = naverApiClient.searchByKeyword(keyword, size);
	    return naverSearchResponse.getItems()
				      .stream()
				      .map(naverPlace -> toPlace(naverPlace))
				      .distinct()
				      .collect(Collectors.toList());
	} catch (Exception e) {
	    log.error("#####NaverLocalPlaceSearchApiService - searchByKeyword : {}", e.getMessage());
	    return Collections.emptyList();
	}
    }

    private Place toPlace(NaverPlace naverPlace) {
	return Place.builder()
		    .name(naverPlace.getTitle())
		    .address(naverPlace.getAddress())
		    .roadAddress(naverPlace.getRoadAddress())
		    .categoryName(naverPlace.getCategory())
		    .phone(naverPlace.getTelephone())
		    .xCoordinate(naverPlace.getMapx())
		    .yCoordinate(naverPlace.getMapy())
		    .externalService(ExternalServiceCode.NAVER)
		    .build();
    }
}

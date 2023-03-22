package com.river.places.search.service.implementation;

import com.river.places.common.exception.ServiceException;
import com.river.places.external.search.enums.ExternalServiceCode;
import com.river.places.external.search.service.ExternalPlaceSearchApiService;
import com.river.places.external.search.service.implementation.KakaoLocalPlaceSearchApiService;
import com.river.places.external.search.service.implementation.NaverLocalPlaceSearchApiService;
import com.river.places.search.model.Place;
import com.river.places.search.service.PlaceSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PlaceSearchServiceTest {

    PlaceSearchService placeSearchService;
    List<ExternalPlaceSearchApiService> externalPlaceSearchApiServiceList = new ArrayList<>();

    private KakaoLocalPlaceSearchApiService kakaoLocalPlaceSearchApiService;
    private NaverLocalPlaceSearchApiService naverLocalPlaceSearchApiService;

    @BeforeEach
    void setUp() {
	kakaoLocalPlaceSearchApiService = mock(KakaoLocalPlaceSearchApiService.class);
	naverLocalPlaceSearchApiService = mock(NaverLocalPlaceSearchApiService.class);
	externalPlaceSearchApiServiceList.add(kakaoLocalPlaceSearchApiService);
	externalPlaceSearchApiServiceList.add(naverLocalPlaceSearchApiService);
	placeSearchService = new PlaceSearchServiceImpl(externalPlaceSearchApiServiceList);
    }

    @Test
    void retrievePlaceSearchResultByKeyword() {

	when(kakaoLocalPlaceSearchApiService.searchByKeyword(anyString(), anyInt(), anyInt()))
		.thenReturn(getMockKakaoPlace());
	when(naverLocalPlaceSearchApiService.searchByKeyword(anyString(), anyInt(), anyInt()))
		.thenReturn(getMockNaverPlace());

	List<Place> searchResultList = placeSearchService.retrievePlaceSearchResultByKeyword("keyword");

	String[] expectedNames = {"1", "3", "5", "2", "4", "7", "9"};

	int resultListLength = expectedNames.length;
	assertEquals(resultListLength, searchResultList.size());

	for (int i = 0; i < resultListLength; i++) {
	    Place place = searchResultList.get(i);
	    assertEquals(place.getName(), expectedNames[i]);
	}

    }

    @Test
    void 카카오_검색_장애() {
	when(kakaoLocalPlaceSearchApiService.searchByKeyword(anyString(), anyInt(), anyInt()))
		.thenReturn(Collections.emptyList());
	when(naverLocalPlaceSearchApiService.searchByKeyword(anyString(), anyInt(), anyInt()))
		.thenReturn(getMockNaverPlace());
	List<Place> searchResultList = placeSearchService.retrievePlaceSearchResultByKeyword("keyword");
	String[] expectedNames = {"1", "3", "5", "7", "9"};
	int resultListLength = expectedNames.length;
	assertEquals(resultListLength, searchResultList.size());

	for (int i = 0; i < resultListLength; i++) {
	    Place place = searchResultList.get(i);
	    assertEquals(place.getName(), expectedNames[i]);
	}
    }

    @Test
    void 둘다_검색_장애() {
	when(kakaoLocalPlaceSearchApiService.searchByKeyword(anyString(), anyInt(), anyInt()))
		.thenReturn(Collections.emptyList());
	when(naverLocalPlaceSearchApiService.searchByKeyword(anyString(), anyInt(), anyInt()))
		.thenReturn(Collections.emptyList());
	assertThrows(ServiceException.class, () -> placeSearchService.retrievePlaceSearchResultByKeyword("keyword"));
    }

    private List<Place> getMockKakaoPlace() {
	// 1, 2, 3, 4, 5
	List<Place> placeList = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
	    Place place = getPlace(String.valueOf(i + 1), ExternalServiceCode.KAKAO);
	    placeList.add(place);
	}
	return placeList;
    }

    private List<Place> getMockNaverPlace() {
	// 1, 3, 5, 7, 9
	List<Place> placeList = new ArrayList<>();
	for (int i = 0; i < 5; i++) {
	    Place place = getPlace(String.valueOf((i * 2) + 1), ExternalServiceCode.NAVER);
	    placeList.add(place);
	}
	return placeList;
    }

    private Place getPlace(String name, ExternalServiceCode externalServiceCode) {
	return Place.builder()
		    .name(name)
		    .roadAddress(name.concat(" ").concat(name))
		    .externalService(externalServiceCode)
		    .build();
    }


}
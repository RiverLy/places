package com.river.places.search.controller;

import com.river.places.search.model.Place;
import com.river.places.search.model.PopularKeyword;
import com.river.places.search.model.response.PlaceSearchResponse;
import com.river.places.search.model.response.PopularKeywordResponse;
import com.river.places.search.service.PlaceSearchMetaService;
import com.river.places.search.service.PlaceSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("v1.*/places/search")
@RestController
public class PlaceSearchController {

    private final PlaceSearchService searchService;
    private final PlaceSearchMetaService searchMetaService;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public PlaceSearchResponse retrievePlaceSearchResultByKeyword(@RequestParam @NotBlank String keyword) {
	searchMetaService.increaseKeywordSearchCount(keyword);
	List<Place> searchedPlaceList = searchService.retrievePlaceSearchResultByKeyword(keyword);
	PlaceSearchResponse placeSearchResponse = PlaceSearchResponse.of(searchedPlaceList);
	return placeSearchResponse;
    }

    @GetMapping(value = "/popular-keywords", produces = MediaType.APPLICATION_JSON_VALUE)
    public PopularKeywordResponse retrieveTopTenPopularPlaceSearchKeywords() {
	List<PopularKeyword> popularKeywordList = searchMetaService.retrieveTopPopularPlaceSearchKeywords(10);
	PopularKeywordResponse popularKeywordResponse = PopularKeywordResponse.of(popularKeywordList);
	return popularKeywordResponse;
    }

}

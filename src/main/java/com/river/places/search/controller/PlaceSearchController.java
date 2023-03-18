package com.river.places.search.controller;

import com.river.places.search.model.Place;
import com.river.places.search.model.PopularKeyword;
import com.river.places.search.model.response.PlaceSearchResponse;
import com.river.places.search.model.response.PopularKeywordResponse;
import com.river.places.search.service.PlaceSearchMetaService;
import com.river.places.search.service.PlaceSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequestMapping("v1.*/places/search")
@RestController
public class PlaceSearchController {

    PlaceSearchService searchService;
    PlaceSearchMetaService searchMetaService;

    @GetMapping("")
    public PlaceSearchResponse retrievePlaceSearchResultByKeyword(@RequestParam String keyword) {
        List<Place> searchedPlaceList = searchService.retrievePlaceSearchResultByKeyword(keyword);
        PlaceSearchResponse placeSearchResponse = PlaceSearchResponse.of(searchedPlaceList);
        return placeSearchResponse;
    }

    @GetMapping("/popular-keywords")
    public PopularKeywordResponse retrieveTopTenPopularPlaceSearchKeywords() {
        List<PopularKeyword> popularKeywordList = searchMetaService.retrieveTopPopularPlaceSearchKeywords(10);
        PopularKeywordResponse popularKeywordResponse = PopularKeywordResponse.of(popularKeywordList);
        return popularKeywordResponse;
    }

}

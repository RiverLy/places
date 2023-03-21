package com.river.places.search.service;

import com.river.places.search.model.Place;

import java.util.List;

public interface PlaceSearchService {
    List<Place> retrievePlaceSearchResultByKeyword(String keyword);
}

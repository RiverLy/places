package com.river.places.search.external.service;

import com.river.places.search.model.Place;

import java.util.List;

public interface ExternalPlaceSearchApiService {
    public List<Place> searchByKeyword(String keyword, int page, int size);

}

package com.river.places.search.service.implementation;

import com.river.places.external.service.ExternalPlaceSearchApiService;
import com.river.places.search.model.Place;
import com.river.places.search.model.response.PlaceSearchResponse;
import com.river.places.search.service.PlaceSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PlaceSearchServiceImpl implements PlaceSearchService {
    @Autowired
    List<ExternalPlaceSearchApiService> externalPlaceSearchApiServiceList;

    @Override
    public List<Place> retrievePlaceSearchResultByKeyword(String keyword) {
        return null;
    }
}

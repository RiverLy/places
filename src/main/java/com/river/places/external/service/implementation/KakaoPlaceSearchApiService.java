package com.river.places.external.service.implementation;

import com.river.places.external.service.ExternalPlaceSearchApiService;
import com.river.places.search.model.Place;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KakaoPlaceSearchApiService implements ExternalPlaceSearchApiService {
    @Override
    public List<Place> searchByKeyword(String keyword, int page, int size) {
        return null;
    }
}

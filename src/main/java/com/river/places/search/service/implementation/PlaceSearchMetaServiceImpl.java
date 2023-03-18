package com.river.places.search.service.implementation;

import com.river.places.search.model.PopularKeyword;
import com.river.places.search.model.response.PopularKeywordResponse;
import com.river.places.search.service.PlaceSearchMetaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaceSearchMetaServiceImpl implements PlaceSearchMetaService {
    @Override
    public List<PopularKeyword> retrieveTopPopularPlaceSearchKeywords(int size) {
        return null;
    }
}

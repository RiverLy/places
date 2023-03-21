package com.river.places.search.service;

import com.river.places.search.model.PopularKeyword;

import java.util.List;

public interface PlaceSearchMetaService {
    List<PopularKeyword> retrieveTopPopularPlaceSearchKeywords(int size);

    void increaseKeywordSearchCount(String keyword);
}

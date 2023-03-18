package com.river.places.search.model.response;

import com.river.places.search.model.PopularKeyword;
import lombok.Setter;

import java.util.List;

@Setter
public class PopularKeywordResponse {

    private int count;
    private List<PopularKeyword> popularKeywords;
    public static PopularKeywordResponse of(List<PopularKeyword> popularKeywordList) {
        PopularKeywordResponse popularKeywordResponse = new PopularKeywordResponse();
        popularKeywordResponse.setCount(popularKeywordList.size());
        popularKeywordResponse.setPopularKeywords(popularKeywordList);
        return popularKeywordResponse;
    }
}

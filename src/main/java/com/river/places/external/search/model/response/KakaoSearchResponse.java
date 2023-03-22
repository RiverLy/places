package com.river.places.external.search.model.response;

import com.river.places.external.search.model.KakaoPlace;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoSearchResponse {
    private List<KakaoPlace> documents;
    private Integer metaTotalCount;
}

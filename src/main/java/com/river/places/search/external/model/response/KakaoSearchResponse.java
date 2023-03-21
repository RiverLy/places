package com.river.places.search.external.model.response;

import com.river.places.search.external.model.KakaoPlace;
import lombok.Getter;

import java.util.List;

@Getter
public class KakaoSearchResponse {
    private List<KakaoPlace> documents;
    private Integer metaTotalCount;
}

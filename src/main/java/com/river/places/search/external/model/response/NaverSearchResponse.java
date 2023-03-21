package com.river.places.search.external.model.response;

import com.river.places.search.external.model.NaverPlace;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NaverSearchResponse {
    private String lastBuildDate;
    private int total;
    private int start;
    private int display;
    private List<NaverPlace> items;
}

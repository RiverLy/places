package com.river.places.external.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NaverPlace {
    private String title;
    private String link;
    private String category;
    private String description;
    private String telephone;
    private String address;
    @JsonProperty("roadAddress")
    private String roadAddress;
    private String mapx;
    private String mapy;
}


package com.river.places.search.external.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoPlace {
    private String id;
    private String placeName;
    private String categoryName;
    private String phone;
    private String addressName;
    private String roadAddressName;
    private String x;
    private String y;

}

package com.river.places.search.model.response;

import com.river.places.search.model.Place;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PlaceSearchResponse implements Serializable {
    private int count;
    private List<Place> places;

    public static PlaceSearchResponse of(List<Place> searchedPlaceList) {
	PlaceSearchResponse placeSearchResponse = new PlaceSearchResponse();
	placeSearchResponse.setCount(searchedPlaceList.size());
	placeSearchResponse.setPlaces(searchedPlaceList);
	return placeSearchResponse;
    }
}

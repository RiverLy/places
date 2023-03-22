package com.river.places.search.service.implementation;

import com.river.places.common.exception.CustomErrorCode;
import com.river.places.common.exception.ServiceException;
import com.river.places.external.search.service.ExternalPlaceSearchApiService;
import com.river.places.search.model.Place;
import com.river.places.search.service.PlaceSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class PlaceSearchServiceImpl implements PlaceSearchService {

    private final List<ExternalPlaceSearchApiService> externalPlaceSearchApiServiceList;
    private final int MAX_SEARCH_RESULT_COUNT = 10;

    @Override
    @Cacheable(value = "searchResult", key = "#keyword")
    public List<Place> retrievePlaceSearchResultByKeyword(String keyword) {
	List<Place> placeList = externalPlaceSearchApiServiceList.parallelStream()
								 .flatMap(apiService -> apiService.searchByKeyword(
									 keyword,
									 1,
									 5).stream())
								 .sorted(Comparator.reverseOrder())
								 .collect(Collectors.toList());
	if (placeList.isEmpty()) {
	    throw new ServiceException(CustomErrorCode.NO_RESULT_FOUND);
	}
	return retrievePriorityBaseSortedPlaceList(placeList);
    }

    private List<Place> retrievePriorityBaseSortedPlaceList(List<Place> placeList) {

	Map<Place, Integer> placeToPriorityWeightMap = new LinkedHashMap<>();

	for (Place place : placeList) {
	    if (placeToPriorityWeightMap.containsKey(place)) {
		int currentWeight = placeToPriorityWeightMap.get(place);
		placeToPriorityWeightMap.replace(place,
						 currentWeight + place.getExternalService().getPriorityWeight());
	    }
	    else {
		placeToPriorityWeightMap.put(place, place.getExternalService().getPriorityWeight());
	    }
	}

	return placeToPriorityWeightMap.entrySet()
				       .stream()
				       .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
				       .limit(MAX_SEARCH_RESULT_COUNT)
				       .map(Map.Entry::getKey)
				       .collect(Collectors.toList());

    }

}

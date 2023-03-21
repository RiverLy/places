package com.river.places.search.service.implementation;

import com.river.places.search.model.PopularKeyword;
import com.river.places.search.service.PlaceSearchMetaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PlaceSearchMetaServiceImpl implements PlaceSearchMetaService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final String KEYWORD_REDIS_KEY = "keywords";

    @Override
    public List<PopularKeyword> retrieveTopPopularPlaceSearchKeywords(int size) {

	List<PopularKeyword> topTenKeywordsList = redisTemplate.opsForZSet()
							       .reverseRangeWithScores(KEYWORD_REDIS_KEY, 0, 9)
							       .stream()
							       .map(PopularKeyword::of)
							       .collect(Collectors.toList());

	return topTenKeywordsList;
    }

    @Override
    public void increaseKeywordSearchCount(String keyword) {

	redisTemplate.opsForZSet().incrementScore(KEYWORD_REDIS_KEY, keyword, 1);

    }

}

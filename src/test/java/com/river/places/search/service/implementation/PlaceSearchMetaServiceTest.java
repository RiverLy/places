package com.river.places.search.service.implementation;

import com.river.places.search.model.PopularKeyword;
import com.river.places.search.service.PlaceSearchMetaService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
class PlaceSearchMetaServiceTest {

    @Autowired
    PlaceSearchMetaService placeSearchMetaService;

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    static final String KEYWORD_REDIS_KEY = "keywords";

    @BeforeEach
    void setUp() {
	for (int i = 1; i <= 15; i++) {
	    redisTemplate.opsForZSet().incrementScore(KEYWORD_REDIS_KEY, "WORD " + i, i);
	}
    }

    @AfterEach
    void clear() {
	ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
	Long size = zSetOps.size(KEYWORD_REDIS_KEY);
	zSetOps.removeRange(KEYWORD_REDIS_KEY, 0, size);
    }


    @Test
    @DisplayName("인기검색어_조회")
    void retrieveTopPopularPlaceSearchKeywords() {
	final int size = 10;
	List<PopularKeyword> popularKeywordList = placeSearchMetaService.retrieveTopPopularPlaceSearchKeywords(size);
	assertEquals(size, popularKeywordList.size());
	for (int i = 0; i < size; i++) {
	    PopularKeyword keyword = popularKeywordList.get(i);
	    final int expectedNum = 15 - i;
	    assertEquals(keyword.getCount(), expectedNum);
	    assertEquals(keyword.getKeyword(), "WORD " + String.valueOf(expectedNum));
	}
    }

    @Test
    @DisplayName("검색횟수_증가")
    void increaseKeywordSearchCount() {
	placeSearchMetaService.increaseKeywordSearchCount("WORD 15");
	List<PopularKeyword> keywordList = redisTemplate.opsForZSet()
							.reverseRangeWithScores(KEYWORD_REDIS_KEY, 0, 1)
							.stream()
							.map(PopularKeyword::of)
							.collect(Collectors.toList());
	assertEquals(16, keywordList.get(0).getCount());
    }

    @Test
    @DisplayName("동시검색_테스트")
    void simultaneousSearch() throws Exception {
	String keyword = "은행";
	ExecutorService executorService = Executors.newFixedThreadPool(100);
	CountDownLatch countDownLatch = new CountDownLatch(100);

	for (int i = 0; i < 100; i++) {
	    executorService.submit(() -> {
		try {
		    placeSearchMetaService.increaseKeywordSearchCount(keyword);
		} finally {
		    countDownLatch.countDown();
		}
	    });
	}
	countDownLatch.await();

	List<PopularKeyword> keywords = placeSearchMetaService.retrieveTopPopularPlaceSearchKeywords(10);

	assertEquals(keywords.get(0).getKeyword(), keyword);
	assertEquals(keywords.get(0).getCount(), 100L);
    }
}
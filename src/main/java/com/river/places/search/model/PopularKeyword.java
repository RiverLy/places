package com.river.places.search.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.ZSetOperations;

@Getter
@Setter
@AllArgsConstructor
@RedisHash("keywords")
public class PopularKeyword {

    private String keyword;
    private Long count;

    public static PopularKeyword of(ZSetOperations.TypedTuple<Object> item) {
	return new PopularKeyword((String) item.getValue(), Math.round(item.getScore()));
    }
}

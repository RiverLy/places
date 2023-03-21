# 검색 서비스

## API

### 1) 검색 API

카카오 검색 API, 네이버 검색 API - 를 통해 각각 최대 5개씩, 총 10개의 키워드 관련 장소를 검색합니다.

[https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword](https://developers.kakao.com/docs/latest/ko/local/dev-guide#search-by-keyword)
[https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD](https://developers.naver.com/docs/serviceapi/search/local/local.md#%EC%A7%80%EC%97%AD)

#### Request

##### Parameter

| Name | Type | Description | Required |
| ---- | ---- | ----------- | -------- |
| keyword | String | 검색 키워드 | O |

#### Response

| Name | Type | Description |
| ---- | ---- | ----------- |
| count | Integer | 총 검색 결과의 수 |

##### places

| Name | Type | Description |
| ---- | ---- | ----------- |
| name | String | 업체명 |
| category\_name | String | 업체 카테고리명 |
| phone | String | 전화번호 |
| address | String | 주소 |
| road\_address | String | 도로명 주소 |

#### 호출 예시

```
curl -X GET "http://localhost:8080/v1.0/places/search?keyword={keyword}"
```

### 2) 인기 검색어 API

사용자들이 많이 검색한 순서대로, 최대 10개의 키워드와 검색된 횟수 목록을 제공합니다.

#### Request

Parameter 없음

#### Response

| Name | Type | Description |
| ---- | ---- | ----------- |
| count | Integer | 총 검색 결과 수 |

##### popular\_keyowrds

| Name | Type | Description |
| ---- | ---- | ----------- |
| keyword | String | 검색어 |
| count | Long | 검색된 총 횟수 |

#### 호출 예시

```
curl -X GET "http://localhost:8080/v1.0/places/search/popular-keywords"
```

## 사용기술

* Language - Java 11
* Framework - Spring Boot 2.7.9
* Database - Redis
* Http Client - OpenFeign

## About Project

* 지역 검색 API, 인기 검색어 조회 API 총 2가지 API를 제공하는 프로젝트입니다.
* 지역 검색 API 호출 시 더 높은 반응성을 고려해 Redis를 이용한 검색어 기준의 캐싱을 지원합니다. (TTL 15 Minute)
* 지역 검색 시 카카오, 네이버 이외의 외부 검색 API 추가를 대비해 외부 API 호출 서비스를 인터페이스로 추상화하여 사용합니다.
* 검색어의 정확한 검색 횟수 측정을 위해 Redis를 사용하여 동시성을 제어하고, Sorted Set 오퍼레이션으로 보다 빠른 검색어 순위를 제공합니다.
* Redis를 별도로 설치하지 않고도 로컬에서 간편하게 테스트해 볼 수 있도록 embedded-redis 라이브러리를 사용합니다.
* 간편하고 명시적인 REST API 호출을 위해 OpenFeign을 사용합니다.
* REST API 호출이 실패할 시, 최대 3번 retry 합니다.
* 하나의 외부 검색 API 호출이 실패하더라도, 다른 API 호출이 정상적이라면 데이터를 반환합니다.
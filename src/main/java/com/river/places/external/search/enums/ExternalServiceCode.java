package com.river.places.external.search.enums;

public enum ExternalServiceCode {
    KAKAO(5),
    NAVER(1);

    private final int priorityWeight;

    ExternalServiceCode(int priorityWeight) {
	this.priorityWeight = priorityWeight;
    }

    public int getPriorityWeight() {
	return priorityWeight;
    }

}

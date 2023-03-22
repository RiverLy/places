package com.river.places.search.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.river.places.external.search.enums.ExternalServiceCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Builder
public class Place implements Comparable<Place>, Serializable {

    @JsonIgnore
    private static final long serialVersionUID = 6727230996935477868L;

    private String name;
    private String categoryName;
    private String phone;
    private String address;
    private String roadAddress;

    @JsonIgnore
    private String xCoordinate;
    @JsonIgnore
    private String yCoordinate;
    @JsonIgnore
    private ExternalServiceCode externalService;

    @Override
    public boolean equals(Object obj) {
	if (obj == this) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (!(obj instanceof Place)) {
	    return false;
	}
	Place otherPlace = (Place) obj;
	return this.getRoadAddressWithNoWhitespaceInLowerCase()
		   .equals(otherPlace.getRoadAddressWithNoWhitespaceInLowerCase());
    }

    @Override
    public int hashCode() {
	return Objects.hash(getRoadAddressWithNoWhitespaceInLowerCase());
    }

    @Override
    public int compareTo(Place o) {
	return Integer.compare(this.externalService.getPriorityWeight(), o.externalService.getPriorityWeight());
    }

    private String getRoadAddressWithNoWhitespaceInLowerCase() {
	return Objects.isNull(roadAddress) ? "" : roadAddress.replaceAll("\\s+", "").toLowerCase();
    }

}

package com.media.instagram.domain;

import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

/**
 * Kakao API 사용
 */
@Entity
@NoArgsConstructor
public class Location {

    @Id
    @Column(name = "location_name")
    String name;

    @Column(name = "location_desc", nullable = true)
    String describe;

    @Column(name = "location_lng")
    BigDecimal longitude;       // 경도
    @Column(name = "location_lat")
    BigDecimal latitude;        // 위도

    public Location(String name) {
        this.name = name;
    }
}

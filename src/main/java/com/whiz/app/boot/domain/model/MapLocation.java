package com.whiz.app.boot.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MapLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MAP_LOCATION_ID")
    @GenericGenerator(
        name = "SEQ_MAP_LOCATION_ID",
        strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
        parameters = {
            @Parameter(name = "sequence_name", value = "SEQ_MAP_LOCATION_ID"),
            @Parameter(name = "initial_value", value = "1"),
            @Parameter(name = "increment_size", value = "1")
        }
    )
    private Long id;
    private String name;
    private String address;
    private String province;
    private String city;
    private String area;
    private String streetId;
    private String loginId;
    private LocalDate dateTime;

    @Override
    public String toString() {
        return "MapLocation#{" +
            "id=" + id +
            ",name=" + name +
            ",address=" + address +
            ",province=" + province +
            ",city=" + city +
            ",area=" + area +
            ",streetId=" + streetId +
            ",loginId=" + loginId +
            ",dateTime=" + dateTime +
            "}";
    }
}
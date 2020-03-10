package com.medinar.covid19tracker.model;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author Rommel Medina
 */
@Getter
@Setter
@ToString
public class LocationStats {

    private String province;
    private String country;
    private List<String> states;
    private String state;
    private String latitude;
    private String longitude;
    private int latestCases;
    private int previousDayCases;
    private int latestTotalCases;
    private int diffFromPrevDay;

}

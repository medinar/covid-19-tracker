package com.medinar.covid19tracker.constant;

/**
 *
 * @author rommelmedina
 */
public class Constants {

    public static final String URL_COVID19_STATS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";

    private Constants() {
    }
    
    public static Constants getInstance() {
        return ConstantsHolder.INSTANCE;
    }
    
    private static class ConstantsHolder {

        private static final Constants INSTANCE = new Constants();
    }
}

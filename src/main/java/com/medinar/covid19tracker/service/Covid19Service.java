package com.medinar.covid19tracker.service;

import com.medinar.covid19tracker.model.LocationStats;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rommel Medina
 */
@Service
public class Covid19Service {

    private static String URL_COVID19_STATS = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv";
    private List<LocationStats> allStats = new ArrayList<>();

    public List<LocationStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* * 1 * * *")
    public void getData() throws IOException {
        try {
            // TODO: FIX: java.net.UnknownHostException:
            List<LocationStats> newStats = new ArrayList<>();
            URL url = new URL(URL_COVID19_STATS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("Connected :)");
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
            System.out.println("+++" + records.toString());
//        for (CSVRecord record : records) {
//            LocationStats locationStat = new LocationStats();
//            locationStat.setState(record.get("Province/State"));
//            locationStat.setCountry(record.get("Country/Region"));
//            newStats.add(locationStat);
//            System.out.println("locationStat: " + locationStat);
//        }
            this.allStats = newStats;
        }
        catch (UnknownHostException ex) {
            System.err.println(ex);
        }
    }
}

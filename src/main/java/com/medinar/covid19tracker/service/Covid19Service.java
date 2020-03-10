package com.medinar.covid19tracker.service;

import static com.medinar.covid19tracker.constant.Constants.FLD_COUNTRY_REGION;
import static com.medinar.covid19tracker.constant.Constants.FLD_PROVINCE_STATE;
import static com.medinar.covid19tracker.constant.Constants.PATH_COVID19_STATS;
import static com.medinar.covid19tracker.constant.Constants.URL_COVID19_STATS;
import com.medinar.covid19tracker.model.LocationStats;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

/**
 *
 * @author Rommel Medina
 */
@Service
public class Covid19Service {

    public List<LocationStats> getLocationStats() throws IOException {
        List<LocationStats> newStats = new ArrayList<>();
        URL url = new URL(URL_COVID19_STATS);
        HttpURLConnection connection;
        InputStream inputStream;
        try {
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
        }
        catch (UnknownHostException ex) {
            System.err.println("Switching to offline mode.");
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            inputStream = classloader.getResourceAsStream(PATH_COVID19_STATS);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);
        for (CSVRecord record : records) {
            LocationStats locationStat = new LocationStats();
            locationStat.setState(record.get(FLD_PROVINCE_STATE));
            locationStat.setCountry(record.get(FLD_COUNTRY_REGION));
            int latestCases = Integer.parseInt(record.get(record.size() - 1));
            int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
            locationStat.setPreviousDayCases(prevDayCases);
            locationStat.setLatestTotalCases(latestCases);
            locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
            newStats.add(locationStat);
        }
        return newStats;
    }

    public List<String> getCountries() throws IOException {
        Set<String> countries = new HashSet<>();
        URL url = new URL(URL_COVID19_STATS);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            countries.add(record.get(FLD_COUNTRY_REGION));
        }

        List<String> sortedCountries = countries.stream().collect(Collectors.toList());
        Collections.sort(sortedCountries, (o1, o2) -> o1.compareTo(o2));
        return sortedCountries;
    }

    public List<LocationStats> getDataByCountry(String country) throws IOException {
        List<LocationStats> locationStats = new ArrayList<>();
        URL url = new URL(URL_COVID19_STATS);
        HttpURLConnection connection;
        InputStream inputStream;
        try {
            connection = (HttpURLConnection) url.openConnection();
            inputStream = connection.getInputStream();
        }
        catch (UnknownHostException ex) {
            System.err.println("Switching to offline mode.");
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            inputStream = classloader.getResourceAsStream(PATH_COVID19_STATS);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(reader);
        for (CSVRecord record : records) {
            if (country.equals(record.get(FLD_COUNTRY_REGION))) {
                LocationStats locationStat = new LocationStats();
                locationStat.setCountry(record.get(FLD_COUNTRY_REGION));
                locationStat.setState(record.get(FLD_PROVINCE_STATE));
                int latestCases = Integer.parseInt(record.get(record.size() - 1));
                int prevDayCases = Integer.parseInt(record.get(record.size() - 2));
                locationStat.setLatestCases(latestCases);
                locationStat.setPreviousDayCases(prevDayCases);
                locationStat.setLatestTotalCases(latestCases);
                locationStat.setDiffFromPrevDay(latestCases - prevDayCases);
                locationStats.add(locationStat);
            }
        }
        return locationStats;
    }

}

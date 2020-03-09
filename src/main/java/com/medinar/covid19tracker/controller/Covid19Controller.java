package com.medinar.covid19tracker.controller;

import com.medinar.covid19tracker.model.LocationStats;
import com.medinar.covid19tracker.service.Covid19Service;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 *
 * @author Rommel Medina
 */
@Controller
public class Covid19Controller {

    @Autowired
    Covid19Service covid19Service;

    @GetMapping("/")
    public String covid19(Model model) throws Exception {
        List<LocationStats> allStats = covid19Service.getLocationStats();
        int totalReportedCases = allStats.stream()
                .mapToInt(stat -> stat.getLatestTotalCases())
                .sum();
        int totalNewCases = allStats.stream()
                .mapToInt(stat -> stat.getDiffFromPrevDay())
                .sum();
        int totalPrevDayCases = allStats.stream()
                .mapToInt(stat -> stat.getPreviousDayCases())
                .sum();        
        model.addAttribute("locationStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalPrevDayCases", totalPrevDayCases);
        return "covid19";
    }

    @GetMapping("/countries")
    public String countries(Model model) throws Exception {
        List<String> countries = covid19Service.getCountries();
        model.addAttribute("totalCountries", countries.size());
        model.addAttribute("countries", countries);
        return "countries";
    }

    @GetMapping(path = "/countries/{country}")
    public String countryAndStates(
            Model model, 
            @PathVariable String country
    ) throws Exception {        
        List<LocationStats> locationStats = covid19Service.getDataByCountry(country);
        int totalReportedCases = locationStats.stream()
                .mapToInt(stat -> stat.getLatestTotalCases())
                .sum();
        int totalNewCases = locationStats.stream()
                .mapToInt(stat -> stat.getDiffFromPrevDay())
                .sum();
        int totalPrevDayCases = locationStats.stream()
                .mapToInt(stat -> stat.getPreviousDayCases())
                .sum();
        model.addAttribute("locationStats", locationStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        model.addAttribute("totalPrevDayCases", totalPrevDayCases);
        return "covid19";
    }
    
}

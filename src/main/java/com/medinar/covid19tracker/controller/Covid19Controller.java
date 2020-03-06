package com.medinar.covid19tracker.controller;

import com.medinar.covid19tracker.model.LocationStats;
import com.medinar.covid19tracker.service.Covid19Service;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author Rommel Medina
 */
@Controller
public class Covid19Controller {

    @Autowired
    Covid19Service covid19Service;
    
    @GetMapping("/")
    public String covid19() {
        return "covid19";
    }
//    @GetMapping("/")
//    public String covid19(Model model) {
//        List<LocationStats> allStats = covid19Service.getAllStats();
//        model.addAttribute("locationStats", allStats);
//        return "covid19";
//    }
}

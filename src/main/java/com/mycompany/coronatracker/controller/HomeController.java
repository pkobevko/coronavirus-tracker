package com.mycompany.coronatracker.controller;

import com.mycompany.coronatracker.entity.LocationStats;
import com.mycompany.coronatracker.service.CoronaVirusDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private CoronaVirusDataService coronaVirusDataService;

    @Autowired
    public HomeController(CoronaVirusDataService coronaVirusDataService) {
        this.coronaVirusDataService = coronaVirusDataService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<LocationStats> allStats = coronaVirusDataService.getAllStats();
        int totalReportedCases = allStats.stream()
                .mapToInt(LocationStats::getLatestTotalCases)
                .sum();
        int totalNewCases = allStats.stream()
                .mapToInt(LocationStats::getDiffFromPrevDay)
                .sum();

        model.addAttribute("locationsStats", allStats);
        model.addAttribute("totalReportedCases", totalReportedCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}

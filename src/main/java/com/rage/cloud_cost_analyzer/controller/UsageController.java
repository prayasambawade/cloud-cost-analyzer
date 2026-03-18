package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.model.Usage;
import com.rage.cloud_cost_analyzer.service.UsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/usage")
@CrossOrigin
public class UsageController {

    @Autowired
    private UsageService service;

    @PostMapping
    public Usage addUsage(@RequestBody Usage usage){
        System.out.println("Received" + usage);
        return service.addUsage(usage);
    }

    @GetMapping
    public List<Usage> getAllUsage(){
        return service.getAllUsage();
    }
    @GetMapping("/total-cost")
    public double totalCost(){
        return service.getTotalCost();
    }
    @GetMapping("/service-cost")
    public Map<String,Double> serviceCost(){
        return service.getCostByService();
    }

    @GetMapping("/top-service")
    public String topService(){
        return service.getTopService();
    }
}

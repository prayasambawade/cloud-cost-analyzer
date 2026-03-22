package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/costs")
@CrossOrigin(origins = "*")

public class CostController {

    @Autowired
    private CostService costService;

    @PostMapping
    public Cost addCost(@RequestBody Cost cost){
        return costService.addCost(cost);
    }

    @GetMapping
    public List<Cost> getAllCost(){
        return costService.getAllCosts();
    }

    @GetMapping("/total/{userId}")
    public double getTotalCost(@PathVariable String userId){
        return costService.getTotalCost(userId);
    }

    @GetMapping("/monthly/{userId}")
    public Map<String,Double> getMonthlyCost(@PathVariable String userId){
        return costService.getMonthlyCost(userId);
    }
    @GetMapping("/totalbyuser/{userId}")
    public double getTotalCostById(@PathVariable String userId){
        return costService.getTotalCost(userId);

    }
}

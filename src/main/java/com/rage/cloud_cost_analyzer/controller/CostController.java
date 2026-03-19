package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

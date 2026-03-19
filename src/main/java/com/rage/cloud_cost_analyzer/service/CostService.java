package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CostService {

    @Autowired
    private CostRepository costRepository;

    public Cost addCost(Cost cost){
        return costRepository.save(cost);
    }

    public List<Cost> getAllCosts(){
        return costRepository.findAll();
    }
}

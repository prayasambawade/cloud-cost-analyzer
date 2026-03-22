package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.repository.CostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    public double getTotalCost(String UserId){
    List<Cost> records = costRepository.findByUserId(UserId);

    double total = 0;

    for (Cost record : records){
        total += record.getAmount();
    }
    return total;
    }

    public Map<String,Double> getMonthlyCost(String userId){
        List<Cost> records = costRepository.findByUserId(userId);

        Map<String,Double> monthlyCost = new HashMap<>();
        for (Cost record : records){
            String month = record.getDate().substring(0,7);
            monthlyCost.put(month,
                    monthlyCost.getOrDefault(month,0.0) + record.getAmount());
        }
        return monthlyCost;
    }

    public double getTotalCostById(String userId){
        List<Cost> costs = costRepository.findByUserId(userId);

        return costs.stream()
                .mapToDouble(Cost::getAmount)
                .sum();
    }

    public Map<String,Double> getDailyCost(String userId){
        List<Cost> costs = costRepository.findByUserId(userId);

        Map<String,Double> daily = new HashMap<>();

        for (Cost c : costs){
            String date = c.getDate().toString();

            daily.put(date,daily.getOrDefault(date,0.0)+c.getAmount());

        }
        return daily;
    }




}

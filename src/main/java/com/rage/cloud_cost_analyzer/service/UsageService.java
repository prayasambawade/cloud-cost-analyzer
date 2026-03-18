package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.Usage;
import com.rage.cloud_cost_analyzer.repository.UsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UsageService {

    @Autowired
    private UsageRepository repository;

    public Usage addUsage(Usage usage){

        // ✅ NULL SAFE CALCULATION
        if (usage.getUsageAmount() != null && usage.getCostPerUnit() != null) {
            usage.setTotalCost(usage.getUsageAmount() * usage.getCostPerUnit());
        } else {
            usage.setTotalCost(0.0);
        }

        return repository.save(usage);
    }

    public List<Usage> getAllUsage(){
        return repository.findAll();
    }

    public double getTotalCost(){
        return repository.findAll()
                .stream()
                .mapToDouble(Usage::getTotalCost)
                .sum();
    }
    public Map<String,Double> getCostByService(){
        List<Usage> list = repository.findAll();
        Map<String,Double> map = new HashMap<>();

        for (Usage u : list ){
            map.put(
                    u.getServiceName(),
                    map.getOrDefault(u.getServiceName(),0.0) + u.getTotalCost()
            );
        }
        return map;
    }

    public String getTopService(){
        return getCostByService()
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No Data");
    }
}
package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.model.Usage;
import com.rage.cloud_cost_analyzer.repository.UsageRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsageService {

    @Autowired
    private UsageRepository repository;

    public Usage addUsage(Usage usage){
        usage.setTotalCost(usage.getUsageAmount() * usage.getCostPerUnit());
        return repository.save(usage);
    }

    public List<Usage> getAllUsage(){
        return repository.findAll();
    }
}

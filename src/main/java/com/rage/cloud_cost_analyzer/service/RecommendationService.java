package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.dto.RecommendationDTO;
import com.rage.cloud_cost_analyzer.model.Cost;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationService {

    public List<RecommendationDTO> generateRecommendation(List<Cost> costs) {

        List<RecommendationDTO> result = new ArrayList<>();

        Map<String,Double> serviceTotal = new HashMap<>();

        double total = 0;




        for(Cost cost : costs){
            serviceTotal.put(
                    cost.getServiceName(),
                    serviceTotal.getOrDefault(cost.getServiceName(), 0.0) + cost.getAmount()
            );
        }
        for(double value : serviceTotal.values()){
            total += value;
        }


        Set<String> processedServices = new HashSet<>();

        for (Map.Entry<String, Double> entry : serviceTotal.entrySet()) {



            String service = entry.getKey();
            double amount = entry.getValue();

            double percentage = total == 0 ? 0 : ((double) amount / total) * 100;

            if (percentage > 20 || amount > 4000) {

                String priority;

                if (percentage > 40) {
                    priority = "HIGH";
                } else if (percentage >= 20) {
                    priority = "MEDIUM";
                } else {
                    priority = "LOW";
                }
                String suggestion;

                if (priority.equals("HIGH")) {
                    suggestion = "Immediate optimization required";
                } else if (priority.equals("MEDIUM")) {
                    suggestion = "Monitor and optimize if needed";
                } else {
                    suggestion = "Cost is under control";
                }

                if (service.equalsIgnoreCase("AWS")) {
                    suggestion = "Use Reserved or Spot Instances";
                }

                result.add(new RecommendationDTO(
                        service,
                        amount,
                        String.format("%.2f%%", percentage),
                        priority,
                        suggestion
                ));
            }

        }
        return result;
    }
}

package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.dto.ServiceCostDTO;
import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.model.User;
import com.rage.cloud_cost_analyzer.repository.CostRepository;
import com.rage.cloud_cost_analyzer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class CostService {

    @Autowired
    private CostRepository costRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;



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

    public List<ServiceCostDTO> getCostByService(String userId){
        return costRepository.getCostByService(userId);
    }

    public String checkBudget(String userId){

        List<Cost> costs = costRepository.findByUserId(userId);

        double total = costs.stream()
                .mapToDouble(Cost :: getAmount)
                .sum();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (total > user.getBudget()) {

            // 🔥 SEND EMAIL
            emailService.sendAlert(
                    user.getEmail(),
                    "⚠️ Budget exceeded!\n\nTotal Cost: " + total +
                            "\nBudget: " + user.getBudget()
            );

            return "⚠️ Budget exceeded! Email sent. Total: " + total;
        }
        else {
            return "✅ Within budget. Total: " + total;
        }
    }

    public Map<String, String> getRecommendation(String userId){
        List<ServiceCostDTO> breakdown = costRepository.getCostByService(userId);

        ServiceCostDTO maxService = breakdown.stream()
                .max((a ,b) -> Double.compare(a.getTotal(),b.getTotal()))
                .orElse(null);

        if (maxService == null){
            return Map.of("Message" , "No Cost data available");
        }

        String service = maxService.getService();
        double cost = maxService.getTotal();

        String message;
        if (service.equalsIgnoreCase("EC2")){
            message = "High EC2 cost detected. Consider Stopping unused instance.";
        } else if (service.equalsIgnoreCase("S3")) {
            message = "High S3 cost . Consider deleting unused storage";

            
        }
        else {
            message = "High cost in " + service + "Review usage";
        }

        return Map.of(
                "Service", service,
                "cost" ,String.valueOf(cost),
                "recommendation" ,message
        );

    }




}

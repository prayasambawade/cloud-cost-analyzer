package com.rage.cloud_cost_analyzer.service;

import com.rage.cloud_cost_analyzer.dto.ServiceCostDTO;
import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.model.User;
import com.rage.cloud_cost_analyzer.repository.CostRepository;
import com.rage.cloud_cost_analyzer.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Autowired
    private AIService aiService;


    @CacheEvict(value = "totalCost", key = "#cost.userId")
    public Cost addCost(Cost cost) {

        if (cost.getDate() == null) {
            cost.setDate(LocalDate.now().toString());
        }

        return costRepository.save(cost);
    }

    public List<Cost> getAllCosts() {
        return costRepository.findAll();
    }

    public String chatWithAI(String userId,String userQuery){

        List<ServiceCostDTO> breakdown = costRepository.getCostByService(userId);

        String context = breakdown.isEmpty() ?
                "No cost data available" : breakdown.toString();

        String prompt = """
        You are a cloud cost optimization assistant.

        User cloud spending:
        """ + context + """

        Answer based on user's data.

        Question:
        """ + userQuery;

        return aiService.getRecommendation(prompt,userId);
    }

    public Page<Cost> getCostsByUser(String userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return costRepository.findByUserId(userId, pageable);
    }

    @Cacheable(value = "totalCost", key = "#userId")
    public double getTotalCost(String userId) {

        System.out.println("🔥 DB HIT: getTotalCost");

        return costRepository.findByUserId(userId)
                .stream()
                .mapToDouble(Cost::getAmount)
                .sum();
    }

    public Map<String, Double> getMonthlyCost(String userId) {
        List<Cost> records = costRepository.findByUserId(userId);

        Map<String, Double> monthlyCost = new HashMap<>();
        for (Cost record : records) {
            String month = record.getDate().substring(0, 7);
            monthlyCost.put(month,
                    monthlyCost.getOrDefault(month, 0.0) + record.getAmount());
        }
        return monthlyCost;
    }

    public double getTotalCostById(String userId) {
        List<Cost> costs = costRepository.findByUserId(userId);

        return costs.stream()
                .mapToDouble(Cost::getAmount)
                .sum();
    }

    public Map<String, Double> getDailyCost(String userId) {
        List<Cost> costs = costRepository.findByUserId(userId);

        Map<String, Double> daily = new HashMap<>();

        for (Cost c : costs) {
            String date = c.getDate().toString();

            daily.put(date, daily.getOrDefault(date, 0.0) + c.getAmount());

        }
        return daily;
    }

    public List<ServiceCostDTO> getCostByService(String userId) {
        return costRepository.getCostByService(userId);
    }

    public String checkBudget(String userId) {

        List<Cost> costs = costRepository.findByUserId(userId);

        double total = costs.stream()
                .mapToDouble(Cost::getAmount)
                .sum();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        if (total > user.getBudget()) {


            emailService.sendAlert(
                    user.getEmail(),
                    "⚠️ Budget exceeded!\n\nTotal Cost: " + total +
                            "\nBudget: " + user.getBudget()
            );

            return "⚠️ Budget exceeded! Email sent. Total: " + total;
        } else {
            return "✅ Within budget. Total: " + total;
        }
    }

    public Map<String, String> getRecommendation(String userId) {

        List<ServiceCostDTO> breakdown = costRepository.getCostByService(userId);

        if (breakdown == null || breakdown.isEmpty()) {
            return Map.of("Message", "No Cost data available");
        }

        ServiceCostDTO maxService = breakdown.stream()
                .max((a, b) -> Double.compare(a.getTotal(), b.getTotal()))
                .orElse(null);

        if (maxService == null) {
            return Map.of("Message", "No Cost data available");
        }

        String service = maxService.getService();
        double cost = maxService.getTotal();

        String prompt = """
                You are a cloud cost optimization expert.

                Highest cost service: %s
                Cost: %.2f

                Give 2-3 practical suggestions to reduce cost.
                Keep answer short and clear.
                """.formatted(service, cost);

        String aiResponse = aiService.getRecommendation(prompt,userId);

        return Map.of(
                "Service", service,
                "cost", String.valueOf(cost),
                "AI_Recommendation", aiResponse
        );
    }
    public String getCostRecommendation(String userId){
        String costData = "AWS........";

        return aiService.getRecommendation(costData,userId);
    }
}





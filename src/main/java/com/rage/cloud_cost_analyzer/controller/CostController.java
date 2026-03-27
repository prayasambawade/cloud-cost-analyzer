package com.rage.cloud_cost_analyzer.controller;

import com.rage.cloud_cost_analyzer.dto.ChatRequest;
import com.rage.cloud_cost_analyzer.dto.ChatResponse;
import com.rage.cloud_cost_analyzer.dto.CostRequestDTO;
import com.rage.cloud_cost_analyzer.dto.ServiceCostDTO;
import com.rage.cloud_cost_analyzer.model.Cost;
import com.rage.cloud_cost_analyzer.service.CostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> addCost(@Valid @RequestBody CostRequestDTO dto){
        Cost cost = new Cost();
        cost.setServiceName(dto.getServiceName());
        cost.setAmount(dto.getAmount());
        cost.setUserId(dto.getUserId());

        Cost saved = costService.addCost(cost);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCosts(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size){

        Page<Cost> costs = costService.getCostsByUser(userId, page, size);

        return ResponseEntity.ok(costs);
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
    @GetMapping("/breakdown/{userId}")
    public List<ServiceCostDTO> getCostBreakDown(@PathVariable String userId){
        return costService.getCostByService(userId);
    }

    @GetMapping("/budget-check/{userId}")
    public String checkBudget(@PathVariable String userId){
        return costService.checkBudget(userId);
    }

    @GetMapping("/recommendation/{userId}")
        public Map<String, String> getRecommendation(@PathVariable String userId ){
        return costService.getRecommendation(userId);
    }

    @GetMapping("/costs/monthly")
    public List<Integer> getMonthlyCost(){
        return List.of(200,300,500,800);
    }

    @GetMapping("/ai/{userId}")
    public ResponseEntity<?> getAIRecommendation(@PathVariable String userId){
        return ResponseEntity.ok(costService.getRecommendation(userId));
    }

    @PostMapping("/chat")
    public ResponseEntity<?> chat (@RequestBody ChatRequest request){
        String reply = costService.chatWithAI(request.getMessage());
        return ResponseEntity.ok(new ChatResponse(reply));
    }

}

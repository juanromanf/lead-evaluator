package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.CitizenshipRecord;
import com.addi.backend.leadevaluator.domain.CriminalRecord;
import com.addi.backend.leadevaluator.domain.LeadEvaluation;
import com.addi.backend.leadevaluator.domain.Person;
import com.addi.backend.leadevaluator.service.api.CitizenshipService;
import com.addi.backend.leadevaluator.service.api.CreditScoreService;
import com.addi.backend.leadevaluator.service.api.CriminalRecordsService;
import com.addi.backend.leadevaluator.service.api.LeadEvaluatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@Service
public class LeadEvaluatorServiceImpl implements LeadEvaluatorService {
    
    private CitizenshipService citizenshipService;
    
    private CriminalRecordsService criminalRecordsService;
    
    private CreditScoreService creditScoreService;
    
    public LeadEvaluatorServiceImpl(CitizenshipService citizenshipService,
                                    CriminalRecordsService criminalRecordsService,
                                    CreditScoreService creditScoreService) {
        this.citizenshipService = citizenshipService;
        this.criminalRecordsService = criminalRecordsService;
        this.creditScoreService = creditScoreService;
    }
    
    @Override
    public CompletionStage<LeadEvaluation> evaluate(Person person) {
        
        CompletionStage<CitizenshipRecord> citizenshipStage = citizenshipService.validate(person).toCompletableFuture();
        
        CompletionStage<List<CriminalRecord>> backgroundStage = criminalRecordsService.checkBackground(person);
        
        CompletableFuture<LeadEvaluation> creditStage = CompletableFuture
                .allOf(citizenshipStage.toCompletableFuture(), backgroundStage.toCompletableFuture())
                .thenCompose(ignore -> creditScoreService.calculateScore(
                        citizenshipStage.toCompletableFuture().join(),
                        backgroundStage.toCompletableFuture().join()))
                .thenApply(s -> LeadEvaluation.builder()
                        .creditScore(s)
                        .identityNumber(person.getIdentityNumber())
                        .status(s >= 60 ? "APPROVED" : "DECLINED")
                        .build());
        
        return creditStage;
    }
}

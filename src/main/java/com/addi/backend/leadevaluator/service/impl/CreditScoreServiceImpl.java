package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.CitizenshipRecord;
import com.addi.backend.leadevaluator.domain.CriminalRecord;
import com.addi.backend.leadevaluator.service.api.CreditScoreService;
import com.addi.backend.leadevaluator.utils.FakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@Service
public class CreditScoreServiceImpl implements CreditScoreService {
    
    @Override
    public CompletionStage<Long> calculateScore(CitizenshipRecord citizenshipRecord, List<CriminalRecord> criminalRecords) {
        
        return CompletableFuture.completedFuture("ignore")
                .thenApply(v -> this.doScoreCalculation(citizenshipRecord, criminalRecords));
    }
    
    private Long doScoreCalculation(CitizenshipRecord citizenshipRecord, List<CriminalRecord> criminalRecords) {
        
        log.debug("calculating credit score for person [{}]...", citizenshipRecord.getIdentityNumber());
        
        FakeUtils.simulateLatency(100, 600);
        
        if (!CollectionUtils.isEmpty(criminalRecords)) {
            
            return FakeUtils.getRandomNumber(0, 59);
        }
        
        return FakeUtils.getRandomNumber(60, 100);
    }
}

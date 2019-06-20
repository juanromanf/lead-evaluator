package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.CriminalRecord;
import com.addi.backend.leadevaluator.domain.Person;
import com.addi.backend.leadevaluator.service.api.CriminalRecordsService;
import com.addi.backend.leadevaluator.utils.FakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@Service
public class CriminalRecordsServiceImpl implements CriminalRecordsService {
    
    @Override
    public CompletionStage<List<CriminalRecord>> checkBackground(Person person) {
        
        return CompletableFuture.completedFuture(person)
                .thenApply(this::doQueryCriminalRecords);
    }
    
    private List<CriminalRecord> doQueryCriminalRecords(Person p) {
        
        log.debug("checking person [{}] criminal records...", p.getIdentityNumber());
        
        FakeUtils.simulateLatency(200, 2000);
        
        if (p.getIdentityNumber().contains("666")) {
            
            CriminalRecord criminalRecord = CriminalRecord.builder()
                    .severity("high")
                    .description("Bank robbery")
                    .date(LocalDate.parse("2017-07-15"))
                    .build();
            
            return Arrays.asList(criminalRecord);
        }
        
        return Collections.emptyList();
    }
    
}

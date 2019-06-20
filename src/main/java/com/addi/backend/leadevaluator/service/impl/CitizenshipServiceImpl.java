package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.CitizenshipRecord;
import com.addi.backend.leadevaluator.domain.Person;
import com.addi.backend.leadevaluator.service.api.CitizenshipService;
import com.addi.backend.leadevaluator.utils.FakeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Slf4j
@Service
public class CitizenshipServiceImpl implements CitizenshipService {
    
    @Override
    public CompletionStage<CitizenshipRecord> validate(Person person) {
        
        return CompletableFuture.completedFuture(person)
                .thenApply(this::doQueryCitizenship);
    }
    
    private CitizenshipRecord doQueryCitizenship(Person person) {
        
        log.debug("checking person [{}] citizenship...", person.getIdentityNumber());
        
        FakeUtils.simulateLatency(100, 800);
        
        return CitizenshipRecord.builder()
                .identityType(person.getIdentityType())
                .identityNumber(person.getIdentityNumber())
                .country("CO")
                .status("ACTIVE")
                .build();
    }
}

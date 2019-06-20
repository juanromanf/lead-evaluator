package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.CitizenshipRecord;
import com.addi.backend.leadevaluator.domain.CriminalRecord;
import com.addi.backend.leadevaluator.service.api.CreditScoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;

public class CreditScoreServiceImplTest {
    
    private CreditScoreService creditScoreService;
    
    @Before
    public void setUp() {
        
        creditScoreService = new CreditScoreServiceImpl();
    }
    
    @Test
    public void shouldReturnUnder60ScoreWithCriminalRecords() {
        
        CitizenshipRecord citizenshipRecord = CitizenshipRecord.builder()
                .identityNumber("111222")
                .country("CO")
                .status("ACTIVE")
                .build();
        
        CriminalRecord criminalRecord = CriminalRecord.builder()
                .severity("high")
                .description("Bank robbery")
                .date(LocalDate.parse("1987-05-20"))
                .build();
        
        Long score = creditScoreService.calculateScore(citizenshipRecord, Arrays.asList(criminalRecord))
                .toCompletableFuture().join();
        
        Assert.assertTrue(score < 60);
    }
    
    @Test
    public void shouldReturnUpper60ScoreWithoutCriminalRecords() {
        
        CitizenshipRecord citizenshipRecord = CitizenshipRecord.builder()
                .identityNumber("111222")
                .country("CO")
                .status("ACTIVE")
                .build();
        
        Long score = creditScoreService.calculateScore(citizenshipRecord, Collections.emptyList())
                .toCompletableFuture().join();
        
        Assert.assertTrue(score >= 60);
    }
}

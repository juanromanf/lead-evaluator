package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.LeadEvaluation;
import com.addi.backend.leadevaluator.domain.Person;
import com.addi.backend.leadevaluator.service.api.LeadEvaluatorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

public class LeadEvaluatorServiceImplTest {
    
    private LeadEvaluatorService leadEvaluatorService;
    
    @Before
    public void setUp() {
        
        leadEvaluatorService = new LeadEvaluatorServiceImpl(
                new CitizenshipServiceImpl(),
                new CriminalRecordsServiceImpl(),
                new CreditScoreServiceImpl());
    }
    
    @Test
    public void leadShouldApproveWithoutCriminalRecords() {
        
        Person p = Person.builder()
                .identityType("cc")
                .identityNumber("111333")
                .identityDate(LocalDate.parse("2000-05-20"))
                .lastName("Sanchez")
                .fistName("Carlos")
                .type("natural")
                .build();
        
        LeadEvaluation evaluation = leadEvaluatorService.evaluate(p)
                .toCompletableFuture().join();
        
        Assert.assertNotNull(evaluation);
        Assert.assertEquals(p.getIdentityNumber(), evaluation.getIdentityNumber());
        Assert.assertEquals("APPROVED", evaluation.getStatus());
        Assert.assertTrue(evaluation.getCreditScore() >= 60);
    }
    
    
    @Test
    public void leadShouldDeclineWithCriminalRecords() {
        
        Person p = Person.builder()
                .identityType("cc")
                .identityNumber("111666") // evil id :|
                .identityDate(LocalDate.parse("1994-05-20"))
                .lastName("Sanchez")
                .fistName("Humberto")
                .type("natural")
                .build();
        
        LeadEvaluation evaluation = leadEvaluatorService.evaluate(p)
                .toCompletableFuture().join();
        
        Assert.assertNotNull(evaluation);
        Assert.assertEquals(p.getIdentityNumber(), evaluation.getIdentityNumber());
        Assert.assertEquals("DECLINED", evaluation.getStatus());
        Assert.assertTrue(evaluation.getCreditScore() < 60);
    }
}

package com.addi.backend.leadevaluator.service.impl;

import com.addi.backend.leadevaluator.domain.CriminalRecord;
import com.addi.backend.leadevaluator.domain.Person;
import com.addi.backend.leadevaluator.service.api.CriminalRecordsService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

public class CriminalRecordsServiceImplTest {
    
    private CriminalRecordsService criminalRecordsService;
    
    @Before
    public void setUp() {
        
        criminalRecordsService = new CriminalRecordsServiceImpl();
    }
    
    @Test
    public void shouldReturnNoRecordsForGoodPerson() {
        
        Person p = Person.builder()
                .identityType("cc")
                .identityNumber("111333") // no evil id :)
                .identityDate(LocalDate.parse("2000-05-20"))
                .lastName("Sanchez")
                .fistName("Carlos")
                .type("natural")
                .build();
        
        List<CriminalRecord> records = criminalRecordsService.checkBackground(p)
                .toCompletableFuture().join();
        
        Assert.assertNotNull(records);
        Assert.assertTrue(records.isEmpty());
    }
    
    
    @Test
    public void shouldReturnRecordsForBadPerson() {
        
        Person p = Person.builder()
                .identityType("cc")
                .identityNumber("111666") // evil id :|
                .identityDate(LocalDate.parse("1994-05-20"))
                .lastName("Sanchez")
                .fistName("Humberto")
                .type("natural")
                .build();
        
        List<CriminalRecord> records = criminalRecordsService.checkBackground(p)
                .toCompletableFuture().join();
        
        Assert.assertNotNull(records);
        Assert.assertFalse(records.isEmpty());
    }
}

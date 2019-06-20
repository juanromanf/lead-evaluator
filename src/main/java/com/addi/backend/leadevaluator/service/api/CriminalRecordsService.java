package com.addi.backend.leadevaluator.service.api;

import com.addi.backend.leadevaluator.domain.CriminalRecord;
import com.addi.backend.leadevaluator.domain.Person;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface CriminalRecordsService {
    
    CompletionStage<List<CriminalRecord>> checkBackground(Person person);
}

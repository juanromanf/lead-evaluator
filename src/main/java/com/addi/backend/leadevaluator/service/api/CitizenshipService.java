package com.addi.backend.leadevaluator.service.api;

import com.addi.backend.leadevaluator.domain.CitizenshipRecord;
import com.addi.backend.leadevaluator.domain.Person;

import java.util.concurrent.CompletionStage;

public interface CitizenshipService {
    
    CompletionStage<CitizenshipRecord> validate(Person person);
}

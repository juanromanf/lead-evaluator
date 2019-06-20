package com.addi.backend.leadevaluator.service.api;

import com.addi.backend.leadevaluator.domain.LeadEvaluation;
import com.addi.backend.leadevaluator.domain.Person;

import java.util.concurrent.CompletionStage;

public interface LeadEvaluatorService {
    
    CompletionStage<LeadEvaluation> evaluate(Person person);
    
}

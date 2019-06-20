package com.addi.backend.leadevaluator.service.api;

import com.addi.backend.leadevaluator.domain.CitizenshipRecord;
import com.addi.backend.leadevaluator.domain.CriminalRecord;

import java.util.List;
import java.util.concurrent.CompletionStage;

public interface CreditScoreService {
    
    CompletionStage<Long> calculateScore(CitizenshipRecord citizenshipRecord, List<CriminalRecord> criminalRecords);
}

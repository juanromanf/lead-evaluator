package com.addi.backend.leadevaluator.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LeadEvaluation {
    
    String identityNumber;
    
    Long creditScore;
    
    String status;
}

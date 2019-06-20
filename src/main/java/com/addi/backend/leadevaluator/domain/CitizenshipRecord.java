package com.addi.backend.leadevaluator.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CitizenshipRecord {
    
    String identityType;
    
    String identityNumber;
    
    String country;
    
    String status;
}

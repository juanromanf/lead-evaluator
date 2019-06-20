package com.addi.backend.leadevaluator.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CriminalRecord {
    
    String severity;
    
    String description;
    
    LocalDate date;
}

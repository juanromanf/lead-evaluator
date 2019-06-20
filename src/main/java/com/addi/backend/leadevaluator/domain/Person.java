package com.addi.backend.leadevaluator.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Person {
    
    String identityType;
    
    String identityNumber;
    
    LocalDate identityDate;
    
    String lastName;
    
    String fistName;
    
    String type;
}

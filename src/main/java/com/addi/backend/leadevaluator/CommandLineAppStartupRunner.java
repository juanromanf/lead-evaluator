package com.addi.backend.leadevaluator;

import com.addi.backend.leadevaluator.domain.LeadEvaluation;
import com.addi.backend.leadevaluator.domain.Person;
import com.addi.backend.leadevaluator.service.api.LeadEvaluatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    
    private LeadEvaluatorService leadEvaluatorService;
    
    public CommandLineAppStartupRunner(LeadEvaluatorService leadEvaluatorService) {
        this.leadEvaluatorService = leadEvaluatorService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        
        StopWatch timer = new StopWatch();
        timer.start("EVALUATOR");
        
        Person p1 = Person.builder()
                .identityType("cc")
                .identityNumber("111666")
                .identityDate(LocalDate.parse("2012-04-20"))
                .lastName("Roman")
                .fistName("Juan")
                .type("natural")
                .build();
        
        Person p2 = Person.builder()
                .identityType("cc")
                .identityNumber("111333")
                .identityDate(LocalDate.parse("2000-05-20"))
                .lastName("Sanchez")
                .fistName("Carlos")
                .type("natural")
                .build();
        
        Person p3 = Person.builder()
                .identityType("cc")
                .identityNumber("111444")
                .identityDate(LocalDate.parse("2011-05-20"))
                .lastName("Ruiz")
                .fistName("Sandra")
                .type("natural")
                .build();
        
        List<LeadEvaluation> results = Arrays.asList(p1, p2, p3)
                .parallelStream()
                .map(p -> {
                    log.debug("starting lead evaluation for: p1 [{}]", p.getIdentityNumber());
                    return leadEvaluatorService.evaluate(p).toCompletableFuture().join();
                })
                .collect(Collectors.toList());
        
        timer.stop();
        
        log.debug("total execution time: {} ms", timer.getTotalTimeMillis());
        
        results.stream()
                .forEach(leadEvaluation -> log.debug("lead evaluation result for person [{}]: score [{}] status [{}]",
                        leadEvaluation.getIdentityNumber(),
                        leadEvaluation.getCreditScore(),
                        leadEvaluation.getStatus()));
    }
}

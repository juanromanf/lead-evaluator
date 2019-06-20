package com.addi.backend.leadevaluator.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeUtils {
    
    public static void simulateLatency(long min, long max) {
        
        try {
            long ms = getRandomNumber(min, max);
            log.debug("latency {} ms...", ms);
            
            Thread.sleep(ms);
            
        } catch (InterruptedException e) {
            log.error("latency error");
        }
    }
    
    public static long getRandomNumber(long min, long max) {
        
        return min + (long) (Math.random() * (max - min));
    }
}

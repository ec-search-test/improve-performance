package eu.europa.ec.digit.search.improveperformance;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class NumberServiceTest {
    
    @Autowired
    NumberService numberService;

    @Test
    void testSpeed() {
        
        List<Integer> data = numberService.generateData();
        
        long start  = System.currentTimeMillis();
        Integer duplicateSlow = numberService.findSmallestDuplicate(data);
        long tookSlow = System.currentTimeMillis() - start;

        start = System.currentTimeMillis();
        Integer duplicateFast = numberService.findSmallestDuplicateImproved(data);
        long tookFast = System.currentTimeMillis() - start;
        
        assertEquals(duplicateSlow, duplicateFast);
        assertTrue(tookSlow > tookFast*1000);
        log.info("slow: {}, fast: {}", tookSlow, tookFast);
        
        
    }
    
    @Test
    void testNull() { 
        
        List<Integer> data = asList(1, 2, 3);
        
        Integer result = numberService.findSmallestDuplicate(data);
        assertNull(result);
        
        result = numberService.findSmallestDuplicateImproved(data);
        assertNull(result);

    }

}

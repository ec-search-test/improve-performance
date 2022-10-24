package eu.europa.ec.digit.search.improveperformance;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Slf4j
@Service
public class NumberService {

    private static final int SAMPLE_SIZE = 100_000;
    private Random random = new Random();

    public Integer findSmallestDuplicate(List<Integer> data) {

        List<Integer> duplicates = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {

            for (int j = i + 1; j < data.size(); j++) {

                if (data.get(i).equals(data.get(j))) {
                    log.info("found duplicate {}", data.get(j));
                    duplicates.add(data.get(j));
                }
            }
        }

        return duplicates.stream().sorted().findFirst().orElse(null);

    }

    public Integer findSmallestDuplicateImproved(List<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        if (data.size() < 10_000) {
            return findSmallestDuplicateImprovedAlternative(data);
        }
        // for large collections it can be faster to work with an array
        return checkDuplicates(data.toArray(new Integer[0]));
    }

    /**
     * Find the smallest duplicate number in a collection by using the Set's property of uniqueness.
     * @param data
     * @return
     */
    private Integer findSmallestDuplicateImprovedAlternative(List<Integer> data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        int smallestDuplicate = Integer.MAX_VALUE;
        Set<Integer> temp = new HashSet<>();
        for (int i = 0; i < data.size(); i++) {
            Integer number = data.get(i);
            if (number < smallestDuplicate && !temp.add(number)) {
                smallestDuplicate = number;
            }
        }
        return smallestDuplicate == Integer.MAX_VALUE ? null : smallestDuplicate;
    }

    private Integer checkDuplicates(Integer[] array) {
        int maxValue = array.length;

        // first pass over the array in order to mark the duplicates (by adding maxValue more than once)
        for (int i = 0; i < array.length; i++) {
            int index = array[i] % maxValue;
            array[index] += maxValue;
        }

        int smallestDuplicate = Integer.MAX_VALUE;
        // now the second pass to get the marked elements and determine the smallest one
        for (int i = 0; i < array.length; i++) {
            if (i < smallestDuplicate && array[i] / maxValue >= 2) {
                smallestDuplicate = i;
            }
        }
        return smallestDuplicate == Integer.MAX_VALUE ? null : smallestDuplicate;
    }

    public List<Integer> generateData() {

        List<Integer> data = IntStream.range(0, SAMPLE_SIZE).boxed().collect(toList());
        
        data.add(data.get(random.nextInt(data.size())));
        log.info("first duplicate number is: {}", data.get(data.size() - 1));
        data.add(data.get(random.nextInt(data.size())));
        log.info("second duplicate number is: {}", data.get(data.size() - 1));
        Collections.shuffle(data);

        return data;
    }
}

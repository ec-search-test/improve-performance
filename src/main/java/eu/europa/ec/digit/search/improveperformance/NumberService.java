package eu.europa.ec.digit.search.improveperformance;

import static java.util.Comparator.*;
import static java.util.stream.Collectors.toList;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NumberService {

    private static final int SAMPLE_SIZE = 100_000;
    private static final int PARALLEL_THRESHOLD = 300_000;

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
        Set<Integer> duplicates = ConcurrentHashMap.newKeySet();
        Set<Integer> seen = ConcurrentHashMap.newKeySet(data.size());
        Stream<Integer> stream = data.size() < PARALLEL_THRESHOLD ?
                data.stream() : data.parallelStream();

        stream.forEach(n -> {
            if (!seen.add(n)) {
                duplicates.add(n);
                log.info("found duplicate {}", n);
            }
        });

        return duplicates.stream().min(naturalOrder()).orElse(null);
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

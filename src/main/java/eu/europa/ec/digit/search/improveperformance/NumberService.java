package eu.europa.ec.digit.search.improveperformance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class NumberService {

    public Integer findSmallestDuplicate(List<Integer> data) {

        for (int i = 0; i < data.size(); i++) {

            for (int j = i + 1; j < data.size(); j++) {

                if (data.get(i).equals(data.get(j))) {

                    log.info("found number {}", data.get(j));
                    return data.get(j);
                }
            }
        }

        return 0;

    }

    public Integer findSmallestDuplicateImproved(List<Integer> data) {

        return 0;

    }

    public List<Integer> generateData() {

        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 100000; i++) {

            data.add(i);
        }

        Random r = new Random();
        data.add(data.get(r.nextInt(data.size())));
        log.info("number is: {}", data.get(data.size() - 1));
        Collections.shuffle(data);

        return data;
    }
}

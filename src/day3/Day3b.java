package day3;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;


public class Day3b {

    public static void main(String[] args) throws Exception {
        List<String> measurements = Files.readAllLines(Paths.get(Day3b.class.getResource("input.txt").toURI()));

        String oxygenGeneratorRating = findDiagnostigRating(measurements, 0, BitCriteriaStrategy.MOST_COMMON);

        String co2ScrubberRating = findDiagnostigRating(measurements, 0,  BitCriteriaStrategy.LEAST_COMMON);

        System.out.println("oxygenGeneratorRating :  " + oxygenGeneratorRating + " co2ScrubberRating: " + co2ScrubberRating +
                " squared total: " + (Integer.parseInt(oxygenGeneratorRating, 2) * Integer.parseInt(co2ScrubberRating, 2)));
    }

    static String findDiagnostigRating(List<String> measurements, int bitPosition, BitCriteriaStrategy bitCriteriaStrategy) {
        Integer numberOfOnes = measurements.stream()
                .map(it -> String.valueOf(it.charAt(bitPosition)))
                .map(Integer::valueOf)
                .reduce(Integer::sum)
                .get();

        char bitCriteria;
        if (numberOfOnes >= (float) measurements.size() / 2) {
            // More 1 than 0. Most common is 1.
            bitCriteria = bitCriteriaStrategy == BitCriteriaStrategy.MOST_COMMON ? '1' : '0';
        } else {
            // most common is 0
            bitCriteria =  bitCriteriaStrategy == BitCriteriaStrategy.MOST_COMMON ? '0' : '1';
        }

        List<String> filtered = measurements.stream()
                .filter(it -> it.charAt(bitPosition) == bitCriteria)
                .collect(Collectors.toList());

        if (filtered.size() == 1) {
            return filtered.get(0);
        }

        return findDiagnostigRating(filtered, bitPosition + 1, bitCriteriaStrategy);
    }

    enum BitCriteriaStrategy {
        LEAST_COMMON,
        MOST_COMMON
    }
}

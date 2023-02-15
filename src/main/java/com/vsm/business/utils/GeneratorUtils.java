package com.vsm.business.utils;

import java.util.*;
import java.util.stream.Collectors;

public class GeneratorUtils {
    public static String _generatePassword(int length, List<GeneratorDataSet> generateDataSetList) {
        Random random = new Random();
        List<Character> chars = new ArrayList<>();
        for (GeneratorDataSet generatorDataSet : generateDataSetList) {
            int counter = 0;
            while (counter < generatorDataSet.getMinimum()) {
                Character c = generatorDataSet.getSeed().charAt(random.nextInt(generatorDataSet.getSeed().length()));
                chars.add(c);
                counter++;
            }
        }
        String combinedChars = generateDataSetList.stream().map(e -> e.getSeed()).collect(Collectors.joining(""));
        while (chars.size() < length) {
            Character c = combinedChars.charAt(random.nextInt(combinedChars.length()));
            chars.add(c);
        }
        Collections.shuffle(chars);
        String result = chars.stream().map(e -> {
            return e.toString();
        }).collect(Collectors.joining(""));
        return result;
    }

    static class GeneratorDataSet {
        String seed;
        int priority;
        int minimum;

        public GeneratorDataSet(String seed) {
            this.seed = seed;
            this.minimum = 0;
        }

        public String getSeed() {
            return seed;
        }

        public void setSeed(String seed) {
            this.seed = seed;
        }

        public int getMinimum() {
            return minimum;
        }

        public void setMinimum(int minimum) {
            this.minimum = minimum;
        }

        public int getPriority() {
            return priority;
        }

        public void setPriority(int priority) {
            this.priority = priority;
        }

        @Override
        public String toString() {
            return "GeneratorDataSet{" +
                "seed='" + seed + '\'' +
                ", priority=" + priority +
                ", minimum=" + minimum +
                '}';
        }
    }

    interface EnglishGeneratorDataSet {
        public GeneratorDataSet LOWERCASE = new GeneratorDataSet("abcdefghijklmnopqrstuvwxyz");
        public GeneratorDataSet UPPERCASE = new GeneratorDataSet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    interface SpecialGeneratorDataSet {
        public GeneratorDataSet SPECIAL = new GeneratorDataSet("!\"#$%&'()*+,-./:;<=>?");
    }

    interface NumberGeneratorDataSet {
        public GeneratorDataSet NUMBER = new GeneratorDataSet("0123456789");
    }

    public static String generateVCRPassword() {
        List<GeneratorDataSet> generatorDataSetList = new ArrayList<>();
        GeneratorDataSet lower = EnglishGeneratorDataSet.LOWERCASE;
        lower.setMinimum(1);
        generatorDataSetList.add(lower);
        GeneratorDataSet number = NumberGeneratorDataSet.NUMBER;
        number.setMinimum(1);
        generatorDataSetList.add(number);
        GeneratorDataSet special = SpecialGeneratorDataSet.SPECIAL;
        special.setMinimum(1);
        generatorDataSetList.add(special);
        return _generatePassword(8, generatorDataSetList);
    }
}

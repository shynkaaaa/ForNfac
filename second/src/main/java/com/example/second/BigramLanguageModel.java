    package com.example.second;

    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Paths;
    import java.util.*;

    public class BigramLanguageModel {
        final Map<String, Integer> bigramCounts;

        public BigramLanguageModel(String fileName) {
            bigramCounts = new HashMap<>();
            List<String> names = new ArrayList<>();

            try {
                List<String> lines = Files.readAllLines(Paths.get(fileName));
                for (String line : lines) {
                    names.add(line.toLowerCase());
                    for (int i = 0; i < line.length() - 1; i++) {
                        String bigram = line.substring(i, i + 2);
                        bigramCounts.put(bigram, bigramCounts.getOrDefault(bigram, 0) + 1);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static String generateName(Map<String, Integer> bigramCounts) {
            StringBuilder name = new StringBuilder();
            Random rand = new Random();
            List<String> possibleStarts = new ArrayList<>();
            for (String bigram : bigramCounts.keySet()) {
                if (bigram.startsWith("^")) {
                    possibleStarts.add(bigram);
                }
            }

            if (possibleStarts.size() == 0) {
                return "";
            }
            String currBigram = possibleStarts.get(rand.nextInt(possibleStarts.size()));

            name.append(currBigram.charAt(1));
            while (!currBigram.endsWith("$")) {
                List<String> possibleNext = new ArrayList<>();
                for (String bigram : bigramCounts.keySet()) {
                    if (bigram.startsWith(currBigram.charAt(1) + "")) {
                        possibleNext.add(bigram);
                    }
                }
                currBigram = possibleNext.get(rand.nextInt(possibleNext.size()));
                name.append(currBigram.charAt(1));
            }
            return name.toString();
        }


        public void printBigramProbabilities() {
            int total = 0;
            for (int count : bigramCounts.values()) {
                total += count;
            }

            System.out.println("Bigram\tProbability");
            for (Map.Entry<String, Integer> entry : bigramCounts.entrySet()) {
                String bigram = entry.getKey();
                int count = entry.getValue();
                double probability = (double) count / total;
                System.out.println(bigram + "\t" + probability);
            }
        }

        public int getTotalCount() {
            int total = 0;
            for (int count : bigramCounts.values()) {
                total += count;
            }
            return total;
        }


        public static void main(String[] args) {
            BigramLanguageModel nameGenerator = new BigramLanguageModel("C:\\Users\\shynk\\IdeaProjects\\second\\src\\main\\java\\names.txt");
            nameGenerator.printBigramProbabilities();

            System.out.println(generateName(nameGenerator.bigramCounts));
        }
    }

package hu.urbanovics.doctusoft.indexfajl;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, List<Integer>> indexMap = new HashMap<>();

        Scanner scanner = new Scanner(new FileReader("szoveg.txt"));
        int lineCounter = 0;
        while (scanner.hasNextLine()) {
            lineCounter++;
            String line = scanner.nextLine();
            StringTokenizer tokenizer = new StringTokenizer(line, " ");
            while (tokenizer.hasMoreTokens()) {
                final String token = tokenizer.nextToken().toLowerCase();
                if (!indexMap.containsKey(token)) {
                    indexMap.put(token, new ArrayList<>());
                }
                indexMap.get(token).add(lineCounter);
            }
        }

        ArrayList<Map.Entry<String, List<Integer>>> entries = new ArrayList<>(indexMap.entrySet());
        entries.sort(Comparator.comparing(Map.Entry::getKey));

        try (FileWriter fileWriter = new FileWriter("szoveg-index.txt")) {
            entries.forEach(item -> {
                try {
                    String indexes = String.join(",", item.getValue().stream().map(Object::toString).collect(Collectors.toList()));
                    fileWriter.write(item.getKey());
                    fileWriter.write(" ");
                    fileWriter.write(indexes);
                    fileWriter.write("\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}

package hu.urbanovics.doctusoft.indexfajl;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        Map<String, List<Integer>> indexMap = new HashMap<>();
        int lineCounter = 0;
        try (BufferedReader br = Files.newBufferedReader(Paths.get("szoveg.txt"), StandardCharsets.ISO_8859_1)) {
            String line = null;
            while ((line = br.readLine()) != null) {
                lineCounter++;
                StringTokenizer tokenizer = new StringTokenizer(line, " ");
                while (tokenizer.hasMoreTokens()) {
                    final String token = tokenizer.nextToken().toLowerCase();
                    if (!indexMap.containsKey(token)) {
                        indexMap.put(token, new ArrayList<>());
                    }
                    indexMap.get(token).add(lineCounter);
                }
            }
        }
        ArrayList<Map.Entry<String, List<Integer>>> entries = new ArrayList<>(indexMap.entrySet());

        Collator collator = Collator.getInstance();
        entries.sort((a, b) -> collator.compare(a.getKey(), b.getKey()));

        try (BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("szoveg-index.txt"), StandardCharsets.ISO_8859_1))) {
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

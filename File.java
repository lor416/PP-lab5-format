import java.io.*;
import java.util.*;

class File {
    public List<String> words = new ArrayList<>();

    public void readWords() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        int ch;
        StringBuilder currentWord = new StringBuilder();
        while ((ch = reader.read()) != -1) {
            char c = (char) ch;
            if (c == '\n') {
                if (currentWord.length() > 0) {
                    words.add(currentWord.toString());
                    currentWord = new StringBuilder();
                }
                words.add("\n");
            } else if (Character.isWhitespace(c)) {
                if (currentWord.length() > 0) {
                    words.add(currentWord.toString());
                    currentWord = new StringBuilder();
                }
            } else {
                currentWord.append(c);
            }
        }
        if (currentWord.length() > 0) {
            words.add(currentWord.toString());
        }
        reader.close();
    }

    public void writeText(List<String> lines) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
        writer.close();
    }

    public List<String> formatText(int width) {
        List<String> lines = new ArrayList<>();
        int start = 0;
        boolean red = true;
        while (start < words.size()) {
            StringBuilder line = new StringBuilder();
            int currentWidth = width;
            if (red) {
                line.append("\t");
                currentWidth = width - 4;
                red = false;
            }
            if (start < words.size() && words.get(start).equals("\n")) {
                lines.add("");
                start++;
                red = true;
                continue;
            }

            // когда слово слишком длинное
            if (start < words.size() && words.get(start).length() > currentWidth) {
                String longWord = words.get(start);
                if (currentWidth > 1) {
                    int availableSpace = currentWidth - 1; // Место для дефиса
                    String part1 = longWord.substring(0, availableSpace) + "-";
                    line.append(part1);
                    lines.add(line.toString());
                    words.add(start + 1, longWord.substring(availableSpace));
                } else {
                    line.append(longWord.substring(0, currentWidth));
                    lines.add(line.toString());
                }
                start++;
                continue;
            }
            int sum = 0;
            int wordCount = 0;
            int current = start;

            // сколько слов поместится в строку
            while (current < words.size() && !words.get(current).equals("\n")) {
                int wordLength = words.get(current).length();
                if (sum == 0) {
                    // Первое слово
                    if (wordLength <= currentWidth) {
                        sum = wordLength;
                        wordCount = 1;
                        current++;
                    } else {
                        break;
                    }
                } else {
                    if (sum + 1 + wordLength <= currentWidth) {
                        sum += 1 + wordLength;
                        wordCount++;
                        current++;
                    } else {
                        break;
                    }
                }
            }
            if (wordCount == 0) {
                break;
            }
            line.append(words.get(start));
            if (wordCount > 1) {
                int totalSpaces = currentWidth - sum;
                int spacesBetweenWords = totalSpaces / (wordCount - 1);
                int extraSpaces = totalSpaces % (wordCount - 1);
                for (int i = 1; i < wordCount; i++) {
                    // обычный пробел + дополнительные пробелы для выравнивания
                    line.append(" ");
                    for (int j = 0; j < spacesBetweenWords; j++) {
                        line.append(" ");
                    }
                    if (i <= extraSpaces) {
                        line.append(" ");
                    }
                    line.append(words.get(start + i));
                }
            }
            while (line.length() < width) {
                line.append(" ");
            }

            lines.add(line.toString());
            start += wordCount;
            if (start < words.size() && words.get(start).equals("\n")) {
                red = true;
                start++;
            }
        }
        return lines;
    }
}
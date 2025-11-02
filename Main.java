/*Лабораторная работа 5-2.  Работа с файлами
Имя входного файла: input.txt
Имя выходного файла: output.txt
       Разработать программу, позволяющую выровнять текст в файле согласно заданной ширины
       (ширина вводится с клавиатуры).
       Для выполнения задания необходимо переносить слова согласно правилам переноса.
       Текст во входном файле написан на русском языке.
       Предложения во входном файле могут содержать различные разделители,
       в том числе несколько пробелов подряд.
       В случае невозможности перенести слово так, чтобы выровнять текущую строку
       по ширине, необходимо добавить в данную строку пробелы равномерно.
       Внимание!!! Необходимо создать класс для работы с файлами
       (чтение из файла, запись в файл).
       Примечание: Входные данные считать корректными.

Лабораторная работа 5-2. Java. Работа с файлами*/

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите ширину: ");
        int width = scanner.nextInt();

        File file = new File();

        try {
            file.readWords();
            List<String> lines = file.formatText(width);
            file.writeText(lines);
            System.out.println("Готово!");
        } catch (IOException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}
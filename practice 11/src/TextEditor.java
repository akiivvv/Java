import java.io.*;
import java.util.Scanner;

public class TextEditor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "textfile.txt";

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Записати до файлу");
            System.out.println("2. Прочитати увесь вміст файлу");
            System.out.println("3. Вийти з редактора");
            System.out.print("Оберіть опцію: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Помилка: введіть число від 1 до 3.");
                continue;
            }

            if (choice == 1) {
                System.out.println("Введіть рядок для запису у файл:");
                String input = scanner.nextLine();
                try {
                    FileWriter writer = new FileWriter(fileName, true);
                    writer.write(input + "\n");
                    writer.close();
                    System.out.println("Рядок записано у файл.");
                } catch (IOException e) {
                    System.out.println("Помилка під час запису у файл.");
                }
            } else if (choice == 2) {
                try {
                    FileReader fileReader = new FileReader(fileName);
                    BufferedReader reader = new BufferedReader(fileReader);
                    System.out.println("Вміст файлу:");
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println(line);
                    }
                    reader.close();
                } catch (FileNotFoundException e) {
                    System.out.println("Файл не знайдено");
                } catch (IOException e) {
                    System.out.println("Помилка читання файлу");
                }
            } else if (choice == 3) {
                System.out.println("Вихід з редактора");
                break;
            } else {
                System.out.println("Невірний вибір. Спробуйте ще раз");
            }
        }
        scanner.close();
    }
}

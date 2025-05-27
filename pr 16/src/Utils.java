import java.io.*;
import java.util.*;

public class Utils {
    public static String playerX = "Гравець X";
    public static String playerO = "Гравець O";
    public static int boardSize = 3;

    public static void changeSettings(Scanner scanner) {
        System.out.print("Введіть ім'я гравця X: ");
        playerX = scanner.next();
        System.out.print("Введіть ім'я гравця O: ");
        playerO = scanner.next();

        System.out.print("Введіть розмір поля (3, 5, 7, 9): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Введіть число!");
            scanner.next();
        }
        int size = scanner.nextInt();
        if (size == 3 || size == 5 || size == 7 || size == 9) {
            boardSize = size;
        } else {
            System.out.println("Невірний розмір. Залишено попередній");
        }

        saveConfig();
        System.out.println("Налаштування збережено");
    }

    public static void saveConfig() {
        try (FileWriter writer = new FileWriter("config.txt")) {
            writer.write(playerX + "\n" + playerO + "\n" + boardSize + "\n");
        } catch (IOException e) {
            System.out.println("Помилка при збереженні налаштувань");
        }
    }

    public static void loadConfig() {
        try {
            File file = new File("config.txt");
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                if (fileScanner.hasNextLine()) playerX = fileScanner.nextLine();
                if (fileScanner.hasNextLine()) playerO = fileScanner.nextLine();
                if (fileScanner.hasNextLine()) boardSize = Integer.parseInt(fileScanner.nextLine());
                fileScanner.close();
            }
        } catch (Exception e) {
            System.out.println("Помилка при завантаженні налаштувань");
        }
    }

    public static void saveStats(String winner) {
        try (FileWriter writer = new FileWriter("stats.txt", true)) {
            writer.write("Дата: " + new Date() + "\n");
            writer.write("Переможець: " + winner + "\n");
            writer.write("Гравець X: " + playerX + ", Гравець O: " + playerO + "\n");
            writer.write("Розмір поля: " + boardSize + "\n");
            writer.write("-----------------------------\n");
        } catch (IOException e) {
            System.out.println("Помилка при збереженні статистики");
        }
    }

    public static void showStatistics() {
        try {
            File file = new File("stats.txt");
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    System.out.println(fileScanner.nextLine());
                }
                fileScanner.close();
            } else {
                System.out.println("Статистика відсутня");
            }
        } catch (Exception e) {
            System.out.println("Помилка при читанні статистики");
        }
    }
}

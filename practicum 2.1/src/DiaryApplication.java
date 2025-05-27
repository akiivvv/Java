import utils.FilesystemUtilities;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DiaryApplication {
    private static final LocalDateTime[] dates = new LocalDateTime[50];
    private static final String[] texts = new String[50];
    private static int size = 0;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static String formatExample = "2024-04-28 13:45";

    public void start() {
        Scanner scan = new Scanner(System.in);

        System.out.println("Привіт в щоденнику");
        System.out.println("1 - створити новий");
        System.out.println("2 - відкрити існуючий");
        int choice = scan.nextInt();
        scan.nextLine();

        if (choice == 2) {
            System.out.print("Введіть шлях до файлу: ");
            String path = scan.nextLine();
            size = FilesystemUtilities.load(path, dates, texts);
        }

        System.out.println("Оберіть як показувати дату:");
        System.out.println("1 - yyyy-MM-dd HH:mm");
        System.out.println("2 - dd.MM.yyyy HH:mm");
        System.out.println("3 - Свій формат");

        int f = scan.nextInt();
        scan.nextLine();
        if (f == 1) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            formatExample = "2024-04-28 13:45";
        } else if (f == 2) {
            formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            formatExample = "28.04.2024 13:45";
        } else if (f == 3) {
            System.out.print("Введіть свій формат: ");
            String custom = scan.nextLine();
            try {
                formatter = DateTimeFormatter.ofPattern(custom);
                formatExample = "Ваш формат (введіть уважно)";
            } catch (Exception e) {
                System.out.println("Формат не правильний, залишаю стандартний");
            }
        }

        int op;
        do {
            System.out.println("\nМеню:");
            System.out.println("1 - додати запис");
            System.out.println("2 - видалити запис");
            System.out.println("3 - подивитись записи");
            System.out.println("4 - вихід");
            op = scan.nextInt();
            scan.nextLine();

            if (op == 1) {
                addEntry(scan);
            } else if (op == 2) {
                removeEntry(scan);
            } else if (op == 3) {
                showEntries();
            }
        } while (op != 4);

        System.out.println("Ви хочете зберегти щоденник? (так/ні)");
        String ans = scan.nextLine();
        if (ans.equalsIgnoreCase("так")) {
            System.out.print("Введіть шлях до файлу для збереження: ");
            String file = scan.nextLine();
            FilesystemUtilities.save(file, dates, texts, size);
        }

        System.out.println("Вихід із програми");
    }

    private void addEntry(Scanner scan) {
        if (size >= 50) {
            System.out.println("Досягнуто максимум записів");
            return;
        }

        System.out.println("Введіть дату (наприклад: " + formatExample + "): ");
        String str = scan.nextLine();
        try {
            LocalDateTime dt = LocalDateTime.parse(str, formatter);
            System.out.println("Введіть текст запису (порожній рядок — кінець):");
            StringBuilder text = new StringBuilder();
            while (true) {
                String line = scan.nextLine();
                if (line.isEmpty()) break;
                text.append(line).append("\n");
            }
            dates[size] = dt;
            texts[size] = text.toString();
            size++;
            System.out.println("Запис додано!");
        } catch (Exception e) {
            System.out.println("Помилка у введенні дати");
        }
    }

    private void removeEntry(Scanner scan) {
        System.out.println("Введіть дату запису який хочете видалити (наприклад: " + formatExample + "): ");
        String s = scan.nextLine();
        try {
            LocalDateTime target = LocalDateTime.parse(s, formatter);
            boolean found = false;
            for (int i = 0; i < size; i++) {
                if (dates[i].isEqual(target)) {
                    for (int j = i; j < size - 1; j++) {
                        dates[j] = dates[j + 1];
                        texts[j] = texts[j + 1];
                    }
                    size--;
                    found = true;
                    System.out.println("Запис видалено");
                    break;
                }
            }
            if (!found) {
                System.out.println("Не знайдено запис з такою датою");
            }
        } catch (Exception e) {
            System.out.println("Помилка при введенні дати");
        }
    }

    private void showEntries() {
        if (size == 0) {
            System.out.println("Немає записів");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println("Дата: " + dates[i].format(formatter));
            System.out.println(texts[i]);
        }
    }
}

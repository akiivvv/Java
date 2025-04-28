import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MyDiary {

    static LocalDateTime[] dates = new LocalDateTime[50];
    static String[] texts = new String[50];
    static int size = 0;
    static DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    static String formatExample = "2024-04-28 13:45";

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Привіт в щоденнику");
        System.out.println("1 - створити новий");
        System.out.println("2 - відкрити існуючий");
        int choise = scan.nextInt();
        scan.nextLine();

        if (choise == 2) {
            System.out.print("Введіть шлях до файлу: ");
            String path = scan.nextLine();
            load(path);
        }

        System.out.println("Оберіть як показувати дату:");
        System.out.println("1 - yyyy-MM-dd HH:mm");
        System.out.println("2 - dd.MM.yyyy HH:mm");
        System.out.println("3 - Свій формат");
        int f = scan.nextInt();
        scan.nextLine();
        if (f == 1) {
            formater = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            formatExample = "2024-04-28 13:45";
        } else if (f == 2) {
            formater = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
            formatExample = "28.04.2024 13:45";
        } else if (f == 3) {
            System.out.print("Введіть свій формат: ");
            String custom = scan.nextLine();
            try {
                formater = DateTimeFormatter.ofPattern(custom);
                formatExample = "Ваш формат (введіть уважно)";
            } catch (Exception e) {
                System.out.println("Формат не правильний, залишаю стандартний");
            }
        }

        int op;
        do {
            System.out.println("Меню:");
            System.out.println("1 - додати запис");
            System.out.println("2 - видалити запис");
            System.out.println("3 - подивитись записи");
            System.out.println("4 - вихід");
            op = scan.nextInt();
            scan.nextLine();
            if (op == 1) {
                add(scan);
            } else if (op == 2) {
                remove(scan);
            } else if (op == 3) {
                show();
            }
        } while (op != 4);

        System.out.println("Ви хочете зберегти щоденник? (так/ні)");
        String ans = scan.nextLine();
        if (ans.equals("так")) {
            System.out.print("Введіть шлях до файлу для збереження: ");
            String file = scan.nextLine();
            save(file);
        }
        System.out.println("Вихід із програми");
    }

    static void add(Scanner scan) {
        if (size >= 50) {
            System.out.println("Більше не можна додати записів");
            return;
        }

        System.out.println("Введіть дату (наприклад: " + formatExample + "): ");
        String str = scan.nextLine();
        try {
            LocalDateTime dt = LocalDateTime.parse(str, formater);
            System.out.println("Введіть текст запису (порожній рядок — кінець):");
            String text = "";
            while (true) {
                String line = scan.nextLine();
                if (line.equals("")) {
                    break;
                }
                text += line + "\n";
            }
            dates[size] = dt;
            texts[size] = text;
            size++;
            System.out.println("Запис додано!");
        } catch (Exception e) {
            System.out.println("Помилка у введенні дати");
        }
    }

    static void remove(Scanner scan) {
        System.out.println("Введіть дату запису який хочете видалити (наприклад: " + formatExample + "): ");
        String s = scan.nextLine();
        try {
            LocalDateTime target = LocalDateTime.parse(s, formater);
            boolean found = false;
            for (int i = 0; i < size; i++) {
                if (dates[i].isEqual(target)) {
                    for (int j = i; j < size - 1; j++) {
                        dates[j] = dates[j + 1];
                        texts[j] = texts[j + 1];
                    }
                    size--;
                    System.out.println("Запис видалено");
                    found = true;
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

    static void show() {
        if (size == 0) {
            System.out.println("Немає записів");
            return;
        }
        for (int i = 0; i < size; i++) {
            System.out.println("Дата: " + dates[i].format(formater));
            System.out.println(texts[i]);
        }
    }

    static void save(String path) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(path));
            for (int i = 0; i < size; i++) {
                pw.println(dates[i].format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                pw.println(texts[i].trim());
                pw.println();
            }
            pw.close();
            System.out.println("Файл збережено!");
        } catch (IOException e) {
            System.out.println("Помилка при збереженні");
        }
    }

    static void load(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ((line = br.readLine()) != null && size < 50) {
                if (line.trim().equals("")) continue;
                LocalDateTime d = LocalDateTime.parse(line.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                String text = "";
                while ((line = br.readLine()) != null && !line.trim().equals("")) {
                    text += line + "\n";
                }
                dates[size] = d;
                texts[size] = text;
                size++;
            }
            br.close();
            System.out.println("Щоденник завантажено");
        } catch (Exception e) {
            System.out.println("Помилка при відкритті файлу");
        }
    }
}



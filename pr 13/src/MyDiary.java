import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MyDiary {

    static LocalDateTime[] dates = new LocalDateTime[50];
    static String[] texts = new String[50];
    static int size = 0;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int option;

        do {
            System.out.println(" МІЙ ЩОДЕННИК ");
            System.out.println("1 - Додати запис");
            System.out.println("2 - Видалити запис");
            System.out.println("3 - Подивитися всі записи");
            System.out.println("4 - Вихід");
            System.out.print("Введіть вибір: ");
            option = scan.nextInt();
            scan.nextLine();

            if (option == 1) {
                add(scan);
            } else if (option == 2) {
                remove(scan);
            } else if (option == 3) {
                show();
            }

        } while (option != 4);

        System.out.println("Ви вийшли з щоденника");
    }

    static void add(Scanner scan) {
        if (size >= 50) {
            System.out.println("Місця немає");
            return;
        }

        System.out.print("Введіть дату (yyyy-MM-dd): ");
        String strDate = scan.nextLine();

        try {
            int year = Integer.parseInt(strDate.substring(0, 4));
            int month = Integer.parseInt(strDate.substring(5, 7));
            int day = Integer.parseInt(strDate.substring(8, 10));

            LocalDateTime date = LocalDateTime.of(year, month, day, 0, 0);

            System.out.println("Введіть текст запису (порожній рядок — кінець):");
            String result = "";
            while (true) {
                String line = scan.nextLine();
                if (line.equals("")) {
                    break;
                }
                result += line + "\n";
            }

            dates[size] = date;
            texts[size] = result;
            size++;

            System.out.println("Запис додано");
        } catch (Exception e) {
            System.out.println("Щось не так з датою");
        }
    }

    static void remove(Scanner scan) {
        System.out.print("Дата для видалення (yyyy-MM-dd): ");
        String strDate = scan.nextLine();

        try {
            int year = Integer.parseInt(strDate.substring(0, 4));
            int month = Integer.parseInt(strDate.substring(5, 7));
            int day = Integer.parseInt(strDate.substring(8, 10));

            LocalDateTime target = LocalDateTime.of(year, month, day, 0, 0);

            for (int i = 0; i < size; i++) {
                if (dates[i].isEqual(target)) {
                    for (int j = i; j < size - 1; j++) {
                        dates[j] = dates[j + 1];
                        texts[j] = texts[j + 1];
                    }
                    size--;
                    System.out.println("Запис видалено");
                    return;
                }
            }

            System.out.println("Не знайшов запис");
        } catch (Exception e) {
            System.out.println("Дата невірна.");
        }
    }

    static void show() {
        if (size == 0) {
            System.out.println("Ще нема записів");
            return;
        }

        DateTimeFormatter form = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < size; i++) {
            System.out.println("Дата: " + dates[i].format(form));
            System.out.println(texts[i]);
            System.out.println("---------------");
        }
    }
}
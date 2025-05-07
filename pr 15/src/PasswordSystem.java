import java.util.Scanner;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PasswordSystem {
    int limit = 15;
    String[] usernames = new String[limit];
    String[] passwords = new String[limit];

    String player1Name = "Player1";
    String player2Name = "Player2";
    int boardSize = 3;

    public PasswordSystem() {
        for (int i = 0; i < limit; i++) {
            usernames[i] = " ";
            passwords[i] = " ";
        }
        loadConfig();
    }

    public static void main(String[] args) {
        PasswordSystem ps = new PasswordSystem();
        ps.run();
    }

    void run() {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Меню:");
            System.out.println("1 - Додати користувача");
            System.out.println("2 - Видалити користувача");
            System.out.println("3 - Аутентифікація");
            System.out.println("4 - Вихід");
            System.out.println("5 - Налаштування");
            System.out.println("6 - Показати статистику");
            System.out.print("Введіть номер: ");

            String line = sc.nextLine();

            try {
                int cho = Integer.parseInt(line);
                if (cho == 1) {
                    registerUser(sc);
                } else if (cho == 2) {
                    deleteUser(sc);
                } else if (cho == 3) {
                    authenticateUser(sc);
                } else if (cho == 4) {
                    System.out.println("Вихід");
                    break;
                } else if (cho == 5) {
                    settingsMenu(sc);
                } else if (cho == 6) {
                    viewStats();
                } else {
                    System.out.println("Невірне число");
                }
            } catch (Exception e) {
                System.out.println("Помилка при вводі");
            }
        }
    }

    void registerUser(Scanner sc) {
        try {
            System.out.print("Ім'я користувача: ");
            String name = sc.nextLine();

            if (name.length() < 5 || name.indexOf(" ") >= 0) {
                System.out.println("Помилка: коротке або з пробілом");
                return;
            }

            System.out.print("Пароль: ");
            String pass = sc.nextLine();

            if (!validPassword(pass)) {
                System.out.println("Поганий пароль");
                return;
            }

            for (int i = 0; i < limit; i++) {
                if (usernames[i].equals(" ")) {
                    usernames[i] = name;
                    passwords[i] = pass;
                    System.out.println("Готово");
                    return;
                }
            }

            System.out.println("Забагато користувачів");
        } catch (Exception e) {
            System.out.println("Сталася помилка");
        }
    }

    void deleteUser(Scanner sc) {
        System.out.print("Ім’я користувача на видалення: ");
        String name = sc.nextLine();

        for (int i = 0; i < limit; i++) {
            if (StringsEqual(usernames[i], name)) {
                usernames[i] = " ";
                passwords[i] = " ";
                System.out.println("Видалено");
                return;
            }
        }
        System.out.println("Не знайдено");
    }

    void authenticateUser(Scanner sc) {
        System.out.print("Ім’я: ");
        String name = sc.nextLine();

        System.out.print("Пароль: ");
        String pass = sc.nextLine();

        for (int i = 0; i < limit; i++) {
            if (StringsEqual(usernames[i], name) && StringsEqual(passwords[i], pass)) {
                System.out.println("Успішно");
                simulateGame(sc, name);
                return;
            }
        }

        System.out.println("Помилка входу");
    }

    boolean StringsEqual(String a, String b) {
        if (a.length() != b.length()) return false;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) return false;
        }
        return true;
    }

    boolean validPassword(String pass) {
        if (pass.length() < 10 || pass.indexOf(" ") >= 0) return false;

        int count = 0;
        boolean special = false;
        String[] bad = {"admin", "pass", "password", "qwerty", "ytrewq"};

        for (int i = 0; i < pass.length(); i++) {
            char ch = pass.charAt(i);
            if (ch >= '0' && ch <= '9') count++;
            if (!Character.isLetterOrDigit(ch)) special = true;
        }

        String low = pass.toLowerCase();
        for (int i = 0; i < bad.length; i++) {
            if (low.indexOf(bad[i]) >= 0) return false;
        }

        return count >= 3 && special;
    }

    void settingsMenu(Scanner sc) {
        System.out.print("Ім’я 1-го гравця: ");
        player1Name = sc.nextLine();

        System.out.print("Ім’я 2-го гравця: ");
        player2Name = sc.nextLine();

        System.out.print("Розмір поля: ");
        try {
            int n = Integer.parseInt(sc.nextLine());
            if (n >= 3) {
                boardSize = n;
                saveConfig();
                System.out.println("Оновлено");
            } else {
                System.out.println("Занадто мало");
            }
        } catch (Exception e) {
            System.out.println("Невірне число");
        }
    }

    void saveConfig() {
        try {
            FileWriter fw = new FileWriter("config.txt");
            fw.write(player1Name + "\n");
            fw.write(player2Name + "\n");
            fw.write(boardSize + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Не збереглось");
        }
    }

    void loadConfig() {
        try {
            File f = new File("config.txt");
            if (!f.exists()) return;

            Scanner reader = new Scanner(f);
            if (reader.hasNextLine()) player1Name = reader.nextLine();
            if (reader.hasNextLine()) player2Name = reader.nextLine();
            if (reader.hasNextLine()) boardSize = Integer.parseInt(reader.nextLine());
            reader.close();
        } catch (Exception e) {
            System.out.println("Не завантажено");
        }
    }

    void simulateGame(Scanner sc, String user) {
        System.out.println("Йде гра...");
        System.out.println("Хто виграв? 1 або 2:");
        String who = sc.nextLine();
        String winner = who.equals("1") ? player1Name : player2Name;

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        saveStats(user, winner, boardSize, now);
    }

    void saveStats(String user, String win, int size, String dt) {
        try {
            FileWriter fw = new FileWriter("stats.txt", true);
            fw.write("Гравець: " + user + ", Переможець: " + win + ", Поле: " + size + ", Дата: " + dt + "\n");
            fw.close();
        } catch (Exception e) {
            System.out.println("Не збережено");
        }
    }

    void viewStats() {
        try {
            File f = new File("stats.txt");
            if (!f.exists()) {
                System.out.println("Поки немає статистики");
                return;
            }

            Scanner r = new Scanner(f);
            System.out.println("Статистика:");
            while (r.hasNextLine()) {
                System.out.println(r.nextLine());
            }
            r.close();
        } catch (Exception e) {
            System.out.println("Не прочитано");
        }
    }
}

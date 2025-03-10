import java.util.Scanner;

class UserStorage {
    String[] usernames = new String[15];
    String[] passwords = new String[15];

    void addUser(String username, String password) {
        if (username.length() < 5 || username.contains(" ")) {
            System.out.println("Помилка: Некоректне ім'я!");
            return;
        }

        if (password.length() < 10 || password.contains(" ")) {
            System.out.println("Помилка: Некоректний пароль!");
            return;
        }

        int digits = 0;
        boolean special = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) digits++;
            if (!Character.isLetterOrDigit(c)) special = true;
        }

        if (digits < 3 || !special) {
            System.out.println("Помилка: Пароль має містити 3 цифри і спецсимвол!");
            return;
        }

        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i] == null) {
                usernames[i] = username;
                passwords[i] = password;
                System.out.println("Користувач доданий!");
                return;
            }
        }
        System.out.println("Помилка: Максимум користувачів!");
    }

    void removeUser(String username) {
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i] != null && usernames[i].equals(username)) {
                usernames[i] = null;
                passwords[i] = null;
                System.out.println("Користувач видалений!");
                return;
            }
        }
        System.out.println("Помилка: Користувач не знайдений!");
    }
}

public class Main {
    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1 - Додати");
            System.out.println("2 - Видалити");
            System.out.println("3 - Вийти");
            System.out.print("Оберіть: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.print("Ім'я: ");
                String username = scanner.nextLine();
                System.out.print("Пароль: ");
                String password = scanner.nextLine();
                storage.addUser(username, password);
            } else if (choice == 2) {
                System.out.print("Ім'я: ");
                String username = scanner.nextLine();
                storage.removeUser(username);
            } else if (choice == 3) {
                System.out.println("Вихід...");
                break;
            } else {
                System.out.println("Невірний вибір!");
            }
        }
        scanner.close();
    }
}

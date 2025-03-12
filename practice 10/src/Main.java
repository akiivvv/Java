import java.util.Scanner;

class AuthenticationException extends Exception {
    AuthenticationException(String message) {
        super(message);
    }
}

class UserStorage {
    String[] usernames = new String[15];
    String[] passwords = new String[15];
    String[] bannedPasswords = {"admin", "pass", "password", "qwerty", "ytrewq"};

    void addUser(String username, String password) throws AuthenticationException {
        if (username.length() < 5 || username.contains(" ")) {
            throw new AuthenticationException("Некоректне ім'я!");
        }

        if (password.length() < 10 || password.contains(" ")) {
            throw new AuthenticationException("Некоректний пароль!");
        }

        int digits = 0;
        boolean special = false;
        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) digits++;
            if (!Character.isLetterOrDigit(c)) special = true;
        }

        for (String banned : bannedPasswords) {
            if (password.contains(banned)) {
                throw new AuthenticationException("Пароль містить заборонене слово!");
            }
        }

        if (digits < 3 || !special) {
            throw new AuthenticationException("Пароль має містити 3 цифри і спецсимвол!");
        }

        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i] == null) {
                usernames[i] = username;
                passwords[i] = password;
                System.out.println("Користувач доданий!");
                return;
            }
        }
        throw new AuthenticationException("Максимальна кількість користувачів!");
    }

    void removeUser(String username) throws AuthenticationException {
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i] != null && usernames[i].equals(username)) {
                usernames[i] = null;
                passwords[i] = null;
                System.out.println("Користувач видалений!");
                return;
            }
        }
        throw new AuthenticationException("Користувач не знайдений!");
    }

    void authenticateUser(String username, String password) throws AuthenticationException {
        for (int i = 0; i < usernames.length; i++) {
            if (usernames[i] != null && usernames[i].equals(username) && passwords[i].equals(password)) {
                System.out.println("Аутентифікація успішна!");
                return;
            }
        }
        throw new AuthenticationException("Невірне ім'я або пароль!");
    }
}

public class Main {
    public static void main(String[] args) {
        UserStorage storage = new UserStorage();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1 - Додати");
            System.out.println("2 - Видалити");
            System.out.println("3 - Увійти");
            System.out.println("4 - Вийти");
            System.out.print("Оберіть: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
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
                    System.out.print("Ім'я: ");
                    String username = scanner.nextLine();
                    System.out.print("Пароль: ");
                    String password = scanner.nextLine();
                    storage.authenticateUser(username, password);
                } else if (choice == 4) {
                    System.out.println("Вихід...");
                    break;
                } else {
                    System.out.println("Невірний вибір!");
                }
            } catch (AuthenticationException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
        scanner.close();
    }
}


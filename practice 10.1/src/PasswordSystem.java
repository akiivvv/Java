import java.util.Scanner;

public class PasswordSystem {
    int limit = 15;
    String[] usernames = new String[limit];
    String[] passwords = new String[limit];

    public PasswordSystem() {
        for (int i = 0; i < limit; i++) {
            usernames[i] = " ";
            passwords[i] = " ";
        }
    }

    public static void main(String[] args) {
        PasswordSystem system = new PasswordSystem();
        system.run();
    }

    void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Меню:");
            System.out.println("1 - Додати користувача");
            System.out.println("2 - Видалити користувача");
            System.out.println("3 - Аутентифікувати користувача");
            System.out.println("4 - Вихід");
            System.out.print("Виберіть опцію: ");

            try {
                int choice = Integer.parseInt(scanner.nextLine());
                if (choice == 1) registerUser(scanner);
                else if (choice == 2) deleteUser(scanner);
                else if (choice == 3) authenticateUser(scanner);
                else if (choice == 4) {
                    System.out.println("Програма завершена");
                    break;
                } else {
                    System.out.println("Невірний вибір. Спробуйте знову");
                }
            } catch (NumberFormatException e) {
                System.out.println("Помилка: введіть число");
            }
        }
    }

    void registerUser(Scanner scanner) {
        try {
            System.out.print("Введіть ім'я користувача: ");
            String username = scanner.nextLine();

            if (username.length() < 5 || username.contains(" ")) {
                throw new IllegalArgumentException("Ім'я повинно бути мінімум 5 символів і без пробілів");
            }

            System.out.print("Введіть пароль: ");
            String password = scanner.nextLine();

            if (!validPassword(password)) {
                throw new IllegalArgumentException("Некоректний пароль! Мінімум 10 символів, 3 цифри, спецсимвол, без пробілів");
            }

            for (int i = 0; i < limit; i++) {
                if (usernames[i].equals(" ")) {
                    usernames[i] = username;
                    passwords[i] = password;
                    System.out.println("Користувач зареєстрований");
                    return;
                }
            }
            throw new IllegalStateException("Досягнуто максимальну кількість користувачів");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    void deleteUser(Scanner scanner) {
        try {
            System.out.print("Введіть ім'я користувача для видалення: ");
            String username = scanner.nextLine();

            for (int i = 0; i < limit; i++) {
                if (StringsEqual(usernames[i], username)) {
                    usernames[i] = " ";
                    passwords[i] = " ";
                    System.out.println("Користувач видалений");
                    return;
                }
            }
            throw new IllegalArgumentException("Користувача не знайдено");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    void authenticateUser(Scanner scanner) {
        try {
            System.out.print("Введіть ім'я користувача: ");
            String username = scanner.nextLine();
            System.out.print("Введіть пароль: ");
            String password = scanner.nextLine();

            for (int i = 0; i < limit; i++) {
                if (StringsEqual(usernames[i], username) && StringsEqual(passwords[i], password)) {
                    System.out.println("Користувач аутентифікований");
                    return;
                }
            }
            throw new IllegalArgumentException("Невірне ім'я або пароль");
        } catch (IllegalArgumentException e) {
            System.out.println("Помилка: " + e.getMessage());
        }
    }

    boolean StringsEqual(String a, String b) {
        if (a.length() != b.length()) return false;

        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    boolean validPassword(String password) {
        if (password.length() < 10 || password.contains(" ")) {
            return false;
        }


        int digits = 0;
        boolean hasSpecial = false;
        String bannedPasswords[] = {"admin", "pass", "password", "qwerty", "ytrewq"};

        for (char c : password.toCharArray()) {
            if (Character.isDigit(c)) digits++;
            if (!Character.isLetterOrDigit(c)) hasSpecial = true;
        }

        for (String banned : bannedPasswords) {
            if (password.toLowerCase().contains(banned)) {
                return false;
            }
        }

        return digits >= 3 && hasSpecial;
    }
}
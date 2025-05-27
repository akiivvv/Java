import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Utils.loadConfig();

        while (true) {
            System.out.println("\n Головне меню");
            System.out.println("1. Грати");
            System.out.println("2. Налаштування");
            System.out.println("3. Статистика");
            System.out.println("4. Вихід");
            System.out.print("Ваш вибір: ");
            char choice = scanner.next().charAt(0);

            switch (choice) {
                case '1' -> Game.playGame(scanner);
                case '2' -> Utils.changeSettings(scanner);
                case '3' -> Utils.showStatistics();
                case '4' -> {
                    System.out.println("Вихід з гри");
                    return;
                }
                default -> System.out.println("Невірний вибір");
            }
        }
    }
}

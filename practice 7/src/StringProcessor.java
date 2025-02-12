import java.util.Scanner;

public class StringProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input;

        while (true) {
            System.out.print("Введіть строку: ");
            input = scanner.nextLine().trim();

            int validWords = 0;
            int wordLength = 0;
            boolean inWord = false;

            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c != ' ') {
                    wordLength++;
                    inWord = true;
                } else {
                    if (inWord && wordLength >= 3) {
                        validWords++;
                    }
                    wordLength = 0;
                    inWord = false;
                }
            }
            if (inWord && wordLength >= 3) {
                validWords++;
            }

            if (validWords >= 2) {
                break;
            } else {
                System.out.println("Строка має містити хоча б 2 слова, кожне з яких має не менше 3 символів. Спробуйте ще раз");
            }
        }

        System.out.println("Оберіть операцію:");
        System.out.println("1 - Перевернути всю строку");
        System.out.println("2 - Перевернути кожне слово окремо");

        int choice = scanner.nextInt();
        scanner.nextLine();

        if (choice == 1) {
            String reversed = "";
            for (int i = input.length() - 1; i >= 0; i--) {
                reversed += input.charAt(i);
            }
            System.out.println("Результат: " + reversed);
        } else if (choice == 2) {
            String result = "";
            String word = "";

            for (int i = 0; i <= input.length(); i++) {
                if (i < input.length() && input.charAt(i) != ' ') {
                    word = input.charAt(i) + word;
                } else {
                    result += word;
                    if (i < input.length()) {
                        result += " ";
                    }
                    word = "";
                }
            }
            System.out.println("Результат: " + result);
        } else {
            System.out.println("Невірний вибір.");
        }
    }
}

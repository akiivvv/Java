import java.util.Scanner;

public class SentenceCounter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введіть текст: ");
        String text = scanner.nextLine();

        int sentenceCount = 0, i = 0;

        while (i < text.length()) {
            char ch = text.charAt(i);
            if (ch == '.' || ch == '!' || ch == '?') sentenceCount++;
            i++;
        }

        System.out.println("Кількість речень: " + sentenceCount);
        scanner.close();
    }
}

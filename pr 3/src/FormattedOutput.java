import java.util.Scanner;
import java.text.MessageFormat;

public class FormattedOutput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть ціле число: ");
        int intValue = scanner.nextInt();
        System.out.print("Введіть число з плаваючою точкою: ");
        double doubleValue = scanner.nextDouble();
        scanner.nextLine(); // очищення буфера
        System.out.print("Введіть строку: ");
        String stringValue = scanner.nextLine();
        System.out.print("Введіть логічне значення (true/false): ");
        boolean boolValue = scanner.nextBoolean();


        System.out.println("Ціле число: " + intValue);
        System.out.println("Число з плаваючою точкою: " + doubleValue);
        System.out.println("Строка: " + stringValue);
        System.out.println("Логічне значення: " + boolValue);

        String messageFormat = MessageFormat.format("Число: {0}, Рядок: {1}, Логіка: {2}", intValue, stringValue, boolValue);
        System.out.println(messageFormat);

        System.out.format("Ціле число: %d, Число з плаваючою точкою: %.2f, Строка: %s, Логічне значення: %b%n",
                intValue, doubleValue, stringValue, boolValue);

        System.out.format("Шістнадцяткове представлення цілого числа: %x%n", intValue);
        System.out.format("Вісімкове представлення цілого числа: %o%n", intValue);
        System.out.format("Число з плаваючою точкою (науковий формат): %e%n", doubleValue);
        System.out.format("Фіксована ширина рядка (15 символів): %-15s%n", stringValue);
        System.out.format("Вирівнювання числа з плаваючою точкою: %10.2f%n", doubleValue);
        System.out.format("Логічне значення з символом-заповнювачем: %+10b%n", boolValue);

        scanner.close();
    }
}
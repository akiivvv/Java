import java.util.Scanner;

public class FunctionCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите значение a: ");
        double a = scanner.nextDouble();

        System.out.print("Введите значение b: ");
        double b = scanner.nextDouble();

        System.out.print("Введите значение x: ");
        double x = scanner.nextDouble();

        double result = 0;

        if (x >= 0 && x < 5) {
            result = Math.sin(3 * x);
        } else if (x == 5) {
            result = 2 * Math.exp(a * x - 1) + 1;
        } else if (x > 5 && x <= 12) {
            result = 1 / (b * x - a);
        } else {
            System.out.println("Значение x выходит за допустимые пределы [0, 12].");
            return;
        }

        System.out.println("f(x) = " + result);
    }
}

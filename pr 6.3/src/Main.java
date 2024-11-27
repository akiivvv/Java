import java.util.Random;
import java.util.Scanner;

class Task3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введіть розмір масиву: ");
        int size = scanner.nextInt();
        int[] array = new int[size];

        System.out.println("Згенерований масив:");
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
            System.out.print(array[i] + " ");
        }

        System.out.print("\nВведіть значення, яке хочете замінити: ");
        int oldValue = scanner.nextInt();
        System.out.print("Введіть нове значення: ");
        int newValue = scanner.nextInt();

        for (int i = 0; i < size; i++) {
            if (array[i] == oldValue) {
                array[i] = newValue;
            }
        }

        System.out.println("Оновлений масив:");
        for (int value : array) {
            System.out.print(value + " ");
        }
    }
}

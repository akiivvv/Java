import java.util.Random;
import java.util.Scanner;

class Task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введіть розмір масиву: ");
        int size = scanner.nextInt();
        int[] array = new int[size];

        System.out.print("Введіть 'зростання' або 'спадання': ");
        String order = scanner.next().toLowerCase();

        System.out.println("Згенерований масив:");
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
            System.out.print(array[i] + " ");
        }

        boolean isOrdered = true;
        if (order.equals("зростання")) {
            for (int i = 0; i < size - 1; i++) {
                if (array[i] > array[i + 1]) {
                    isOrdered = false;
                    break;
                }
            }
        } else if (order.equals("спадання")) {
            for (int i = 0; i < size - 1; i++) {
                if (array[i] < array[i + 1]) {
                    isOrdered = false;
                    break;
                }
            }
        } else {
            System.out.println("\nНевірна команда.");
            return;
        }

        System.out.println("\nМасив є " + (isOrdered ? order : "не " + order) + ".");
    }
}

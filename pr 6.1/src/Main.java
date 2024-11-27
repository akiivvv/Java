import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Введіть розмір масиву: ");
        int size = scanner.nextInt();
        int[] array = new int[size];

        int evenCount = 0, oddCount = 0;

        System.out.println("Згенерований масив:");
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100) + 1;
            System.out.print(array[i] + " ");
            if (array[i] % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }
        System.out.println("\nПарних чисел: " + evenCount + ", Непарних чисел: " + oddCount);
    }
}


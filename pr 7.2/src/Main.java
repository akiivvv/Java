import java.util.Random;

public class Main {
    public static void main(String[] args) {
        int rows = 4;
        int cols = 5;
        double[][] array = new double[rows][cols];
        Random random = new Random();

        System.out.println("Початковий масив:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                array[i][j] = random.nextDouble() * 10;
                System.out.printf("%.2f ", array[i][j]);
            }
            System.out.println();
        }

        System.out.println("\nМодифікований масив:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i % 2 != 0 || j % 2 != 0) {
                    array[i][j] = Math.sqrt(array[i][j]);
                }
                System.out.printf("%.2f ", array[i][j]);
            }
            System.out.println();
        }
    }
}

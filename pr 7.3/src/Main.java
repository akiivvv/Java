import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] matrix = new int[5][5];

        System.out.println("Введіть елементи матриці 5x5:");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }

        int determinant = calculateDeterminant(matrix);
        System.out.println("Визначник матриці: " + determinant);
    }

    public static int calculateDeterminant(int[][] matrix) {
        return 0;
    }
}

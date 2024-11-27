import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введіть кількість кутів багатокутника: ");
        int n = scanner.nextInt();

        if (n < 3) {
            System.out.println("Багатокутник має мати хоча б 3 кути.");
            return;
        }

        int[] angles = new int[n];
        int sum = 0;

        System.out.println("Введіть кути багатокутника:");
        for (int i = 0; i < n; i++) {
            angles[i] = scanner.nextInt();
            sum += angles[i];
        }

        int expectedSum = 180 * (n - 2);
        if (sum == expectedSum && allPositive(angles)) {
            System.out.println("Багатокутник можливий.");
        } else {
            System.out.println("Багатокутник неможливий.");
        }
    }

    private static boolean allPositive(int[] angles) {
        for (int angle : angles) {
            if (angle <= 0) return false;
        }
        return true;
    }
}

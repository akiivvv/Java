import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Main {
    static String playerX = "Гравець X";
    static String playerO = "Гравець O";
    static int boardSize = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        loadConfig();

        while (true) {
            System.out.println("\n Головне меню");
            System.out.println("1. Грати");
            System.out.println("2. Налаштування");
            System.out.println("3. Статистика");
            System.out.println("4. Вихід");
            System.out.print("Ваш вибір: ");
            char choice = scanner.next().charAt(0);

            if (choice == '4') {
                System.out.println("Вихід з гри");
                break;
            } else if (choice == '1') {
                playGame(scanner);
            } else if (choice == '2') {
                changeSettings(scanner);
            } else if (choice == '3') {
                showStatistics();
            } else {
                System.out.println("Невірний вибір");
            }
        }

        scanner.close();
    }

    static void playGame(Scanner scanner) {
        char[][] board = new char[boardSize + 1][boardSize + 1];
        for (int i = 0; i <= boardSize; i++) {
            for (int j = 0; j <= boardSize; j++) {
                if (i == 0 || j == 0) {
                    board[i][j] = ' ';
                } else {
                    board[i][j] = '-';
                }
            }
        }

        char currentPlayer = 'X';
        boolean finished = false;
        while (!finished) {
            printBoard(board);
            System.out.println("Хід " + (currentPlayer == 'X' ? playerX : playerO));
            int row = -1;
            int col = -1;

            while (true) {
                System.out.print("Введіть рядок і стовпець: ");
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (row >= 1 && row <= boardSize && col >= 1 && col <= boardSize && board[row][col] == '-') {
                            break;
                        }
                    }
                }
                System.out.println("Невірні координати. Спробуйте ще раз");
                scanner.nextLine();
            }

            board[row][col] = currentPlayer;

            if (checkWin(board, currentPlayer)) {
                printBoard(board);
                System.out.println("Гравець " + (currentPlayer == 'X' ? playerX : playerO) + " переміг!");
                saveStats(currentPlayer == 'X' ? playerX : playerO);
                finished = true;
            } else if (isDraw(board)) {
                printBoard(board);
                System.out.println("Нічия!");
                saveStats("Нічия");
                finished = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    static void printBoard(char[][] board) {
        System.out.print("  ");
        for (int i = 1; i <= boardSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= boardSize; i++) {
            System.out.print(i + " ");
            for (int j = 1; j <= boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean checkWin(char[][] board, char player) {
        for (int i = 1; i <= boardSize; i++) {
            boolean row = true;
            boolean col = true;
            for (int j = 1; j <= boardSize; j++) {
                if (board[i][j] != player) row = false;
                if (board[j][i] != player) col = false;
            }
            if (row || col) return true;
        }

        boolean diag1 = true;
        boolean diag2 = true;
        for (int i = 1; i <= boardSize; i++) {
            if (board[i][i] != player) diag1 = false;
            if (board[i][boardSize - i + 1] != player) diag2 = false;
        }
        return diag1 || diag2;
    }

    static boolean isDraw(char[][] board) {
        for (int i = 1; i <= boardSize; i++) {
            for (int j = 1; j <= boardSize; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    static void changeSettings(Scanner scanner) {
        System.out.print("Введіть ім'я гравця X: ");
        playerX = scanner.next();
        System.out.print("Введіть ім'я гравця O: ");
        playerO = scanner.next();

        System.out.print("Введіть розмір поля (3, 5, 7, 9): ");
        while (!scanner.hasNextInt()) {
            System.out.println("Введіть число!");
            scanner.next();
        }
        int size = scanner.nextInt();
        if (size == 3 || size == 5 || size == 7 || size == 9) {
            boardSize = size;
        } else {
            System.out.println("Невірний розмір. Залишено попередній");
        }

        saveConfig();
        System.out.println("Налаштування збережено");
    }

    static void saveConfig() {
        try {
            FileWriter writer = new FileWriter("config.txt");
            writer.write(playerX + "\n");
            writer.write(playerO + "\n");
            writer.write(boardSize + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Помилка при збереженні налаштувань");
        }
    }

    static void loadConfig() {
        try {
            File file = new File("config.txt");
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                if (fileScanner.hasNextLine()) playerX = fileScanner.nextLine();
                if (fileScanner.hasNextLine()) playerO = fileScanner.nextLine();
                if (fileScanner.hasNextLine()) boardSize = Integer.parseInt(fileScanner.nextLine());
                fileScanner.close();
            }
        } catch (Exception e) {
            System.out.println("Помилка при завантаженні налаштувань");
        }
    }

    static void saveStats(String winner) {
        try {
            FileWriter writer = new FileWriter("stats.txt", true);
            Date now = new Date();
            writer.write("Дата: " + now.toString() + "\n");
            writer.write("Переможець: " + winner + "\n");
            writer.write("Гравець X: " + playerX + ", Гравець O: " + playerO + "\n");
            writer.write("Розмір поля: " + boardSize + "\n");
            writer.write("-----------------------------\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Помилка при збереженні статистики");
        }
    }

    static void showStatistics() {
        try {
            File file = new File("stats.txt");
            if (file.exists()) {
                Scanner fileScanner = new Scanner(file);
                while (fileScanner.hasNextLine()) {
                    System.out.println(fileScanner.nextLine());
                }
                fileScanner.close();
            } else {
                System.out.println("Статистика відсутня");
            }
        } catch (Exception e) {
            System.out.println("Помилка при читанні статистики");
        }
    }
}

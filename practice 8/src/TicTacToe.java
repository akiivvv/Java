import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        mainMenu(scanner);
        scanner.close();
    }

    static void mainMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n Головне меню");
            System.out.println("1. Грати");
            System.out.println("2. Налаштування");
            System.out.println("3. Вихід");
            System.out.print("Ваш вибір: ");

            char choice = scanner.next().charAt(0);
            if (choice == '3') break;

            switch (choice) {
                case '1':
                    playGame(scanner);
                    break;
                case '2':
                    settingsMenu(scanner);
                    break;
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз");
            }
        }
    }

    static void settingsMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n Налаштування ");
            System.out.println("1. Повернутися в головне меню");
            System.out.print("Ваш вибір: ");

            char settingsChoice = scanner.next().charAt(0);
            if (settingsChoice == '1') return;
            System.out.println("Невірний вибір, спробуйте ще раз");
        }
    }

    static void playGame(Scanner scanner) {
        System.out.print("Введіть розмір поля (3, 5, 7, 9): ");
        int boardSize = getValidBoardSize(scanner);
        char[][] board = initializeBoard(boardSize);
        char currentPlayer = 'X';
        boolean gameFinished = false;

        while (!gameFinished) {
            displayBoard(board, boardSize);
            int[] move = getPlayerMove(scanner, board, boardSize, currentPlayer);
            if (move[0] == 0 && move[1] == 0) {
                System.out.println("Гравець вийшов з гри");
                return;
            }
            board[move[0]][move[1]] = currentPlayer;

            if (checkWin(board, boardSize, currentPlayer)) {
                displayBoard(board, boardSize);
                System.out.println("Гравець " + currentPlayer + " переміг");
                gameFinished = true;
            } else if (checkDraw(board, boardSize)) {
                displayBoard(board, boardSize);
                System.out.println("Нічия");
                gameFinished = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    static int getValidBoardSize(Scanner scanner) {
        while (true) {
            while (!scanner.hasNextInt()) {
                System.out.println("Невірний ввід. Введіть число: ");
                scanner.next();
            }
            int size = scanner.nextInt();
            if (size == 3 || size == 5 || size == 7 || size == 9) return size;
            System.out.println("Невірний розмір, спробуйте ще раз");
        }
    }

    static char[][] initializeBoard(int size) {
        char[][] board = new char[size + 1][size + 1];
        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                board[i][j] = (i == 0 || j == 0) ? (char) ('0' + Math.max(i, j)) : '-';
            }
        }
        return board;
    }

    static void displayBoard(char[][] board, int size) {
        System.out.print("    ");
        for (int i = 1; i <= size; i++) System.out.print(i + "   ");
        System.out.println("\n  " + "-".repeat(size * 4 - 1));

        for (int i = 1; i <= size; i++) {
            System.out.print(i + " | ");
            for (int j = 1; j <= size; j++) System.out.print(board[i][j] + " | ");
            System.out.println("\n  " + "-".repeat(size * 4 - 1));
        }
    }

    static int[] getPlayerMove(Scanner scanner, char[][] board, int size, char player) {
        int row, col;
        while (true) {
            System.out.println("Гравець " + player + ", ваш хід (або введіть 0 0 для виходу)");
            System.out.print("Введіть координати: ");

            while (!scanner.hasNextInt()) scanner.next();
            row = scanner.nextInt();
            while (!scanner.hasNextInt()) scanner.next();
            col = scanner.nextInt();

            if (row == 0 && col == 0) return new int[]{0, 0};
            if (row >= 1 && row <= size && col >= 1 && col <= size && board[row][col] == '-') {
                return new int[]{row, col};
            }
            System.out.println("Невірний хід. Спробуйте ще раз");
        }
    }

    static boolean checkWin(char[][] board, int size, char player) {
        for (int i = 1; i <= size; i++) {
            if (checkLine(board, size, player, i, true) || checkLine(board, size, player, i, false)) return true;
        }
        return checkDiagonals(board, size, player);
    }

    static boolean checkLine(char[][] board, int size, char player, int index, boolean isRow) {
        for (int i = 1; i <= size; i++) {
            if ((isRow ? board[index][i] : board[i][index]) != player) return false;
        }
        return true;
    }

    static boolean checkDiagonals(char[][] board, int size, char player) {
        boolean diag1 = true, diag2 = true;
        for (int i = 1; i <= size; i++) {
            if (board[i][i] != player) diag1 = false;
            if (board[i][size - i + 1] != player) diag2 = false;
        }
        return diag1 || diag2;
    }

    static boolean checkDraw(char[][] board, int size) {
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }
}


import java.util.Scanner;

public class Game {
    public static void playGame(Scanner scanner) {
        int size = Utils.boardSize;
        char[][] board = new char[size + 1][size + 1];
        for (int i = 1; i <= size; i++)
            for (int j = 1; j <= size; j++)
                board[i][j] = '-';

        char currentPlayer = 'X';
        boolean finished = false;

        while (!finished) {
            printBoard(board);
            System.out.println("Хід " + (currentPlayer == 'X' ? Utils.playerX : Utils.playerO));
            int row, col;

            while (true) {
                System.out.print("Введіть рядок і стовпець: ");
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (row >= 1 && row <= size && col >= 1 && col <= size && board[row][col] == '-') break;
                    }
                }
                System.out.println("Невірні координати. Спробуйте ще раз");
                scanner.nextLine();
            }

            board[row][col] = currentPlayer;

            if (checkWin(board, currentPlayer)) {
                printBoard(board);
                System.out.println("Гравець " + (currentPlayer == 'X' ? Utils.playerX : Utils.playerO) + " переміг!");
                Utils.saveStats(currentPlayer == 'X' ? Utils.playerX : Utils.playerO);
                finished = true;
            } else if (isDraw(board)) {
                printBoard(board);
                System.out.println("Нічия!");
                Utils.saveStats("Нічия");
                finished = true;
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    static void printBoard(char[][] board) {
        int size = Utils.boardSize;
        System.out.print("  ");
        for (int i = 1; i <= size; i++) System.out.print(i + " ");
        System.out.println();
        for (int i = 1; i <= size; i++) {
            System.out.print(i + " ");
            for (int j = 1; j <= size; j++) System.out.print(board[i][j] + " ");
            System.out.println();
        }
    }

    static boolean checkWin(char[][] board, char player) {
        int size = Utils.boardSize;
        for (int i = 1; i <= size; i++) {
            boolean row = true, col = true;
            for (int j = 1; j <= size; j++) {
                if (board[i][j] != player) row = false;
                if (board[j][i] != player) col = false;
            }
            if (row || col) return true;
        }

        boolean diag1 = true, diag2 = true;
        for (int i = 1; i <= size; i++) {
            if (board[i][i] != player) diag1 = false;
            if (board[i][size - i + 1] != player) diag2 = false;
        }
        return diag1 || diag2;
    }

    static boolean isDraw(char[][] board) {
        int size = Utils.boardSize;
        for (int i = 1; i <= size; i++)
            for (int j = 1; j <= size; j++)
                if (board[i][j] == '-') return false;
        return true;
    }
}


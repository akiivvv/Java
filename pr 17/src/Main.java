import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

class Player {
    String name;
    char symbol;

    Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
    }
}

class GameSettings {
    Player playerX;
    Player playerO;
    int boardSize;

    GameSettings(Player x, Player o, int boardSize) {
        this.playerX = x;
        this.playerO = o;
        this.boardSize = boardSize;
    }
}

public class Main {
    static GameSettings settings = new GameSettings(
            new Player("Гравець X", 'X'),
            new Player("Гравець O", 'O'),
            3
    );

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
        int size = settings.boardSize;
        char[][] board = new char[size + 1][size + 1];

        for (int i = 0; i <= size; i++) {
            for (int j = 0; j <= size; j++) {
                board[i][j] = (i == 0 || j == 0) ? ' ' : '-';
            }
        }

        char currentPlayer = 'X';
        boolean finished = false;

        while (!finished) {
            printBoard(board);
            String currentName = currentPlayer == 'X' ? settings.playerX.name : settings.playerO.name;
            System.out.println("Хід " + currentName);

            int row = -1, col = -1;
            while (true) {
                System.out.print("Введіть рядок і стовпець: ");
                if (scanner.hasNextInt()) {
                    row = scanner.nextInt();
                    if (scanner.hasNextInt()) {
                        col = scanner.nextInt();
                        if (row >= 1 && row <= size && col >= 1 && col <= size && board[row][col] == '-') {
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
                System.out.println("Гравець " + currentName + " переміг!");
                saveStats(currentName);
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
        for (int i = 1; i <= settings.boardSize; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        for (int i = 1; i <= settings.boardSize; i++) {
            System.out.print(i + " ");
            for (int j = 1; j <= settings.boardSize; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    static boolean checkWin(char[][] board, char player) {
        int size = settings.boardSize;
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
        int size = settings.boardSize;
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                if (board[i][j] == '-') return false;
            }
        }
        return true;
    }

    static void changeSettings(Scanner scanner) {
        System.out.print("Введіть ім'я гравця X: ");
        String nameX = scanner.next();
        System.out.print("Введіть ім'я гравця O: ");
        String nameO = scanner.next();

        System.out.print("Введіть розмір поля (3, 5, 7, 9): ");
        int size = 3;
        if (scanner.hasNextInt()) {
            size = scanner.nextInt();
            if (size != 3 && size != 5 && size != 7 && size != 9) {
                System.out.println("Невірний розмір. Залишено попередній");
                size = settings.boardSize;
            }
        } else {
            System.out.println("Невірне значення. Залишено попередній");
            scanner.next();
        }

        settings = new GameSettings(new Player(nameX, 'X'), new Player(nameO, 'O'), size);
        saveConfig();
        System.out.println("Налаштування збережено");
    }

    static void saveConfig() {
        try {
            FileWriter writer = new FileWriter("config.txt");
            writer.write(settings.playerX.name + "\n");
            writer.write(settings.playerO.name + "\n");
            writer.write(settings.boardSize + "\n");
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
                String nameX = fileScanner.nextLine();
                String nameO = fileScanner.nextLine();
                int size = Integer.parseInt(fileScanner.nextLine());
                settings = new GameSettings(new Player(nameX, 'X'), new Player(nameO, 'O'), size);
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
            writer.write("Гравець X: " + settings.playerX.name + ", Гравець O: " + settings.playerO.name + "\n");
            writer.write("Розмір поля: " + settings.boardSize + "\n");
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

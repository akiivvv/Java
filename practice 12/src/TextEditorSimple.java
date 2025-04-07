import java.io.*;
import java.util.Scanner;

public class TextEditorSimple {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "textfile.txt";

        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Додати рядки до файлу");
            System.out.println("2. Показати всі рядки");
            System.out.println("3. Показати рядки з діапазону");
            System.out.println("4. Вставити рядок у конкретне місце");
            System.out.println("5. Вихід");
            System.out.print("Ваш вибір: ");

            int choice = Integer.parseInt(scanner.nextLine());

            if (choice == 1) {
                System.out.println("Вводьте рядки (напишіть exit щоб закінчити):");
                int lineNum = countLines(fileName) + 1;
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
                    while (true) {
                        System.out.print(lineNum + ": ");
                        String line = scanner.nextLine();
                        if (line.equals("exit")) break;
                        bw.write(line);
                        bw.newLine();
                        lineNum++;
                    }
                } catch (IOException e) {
                    System.out.println("Помилка запису.");
                }

            } else if (choice == 2) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    int num = 1;
                    while ((line = br.readLine()) != null) {
                        System.out.println(num + ": " + line);
                        num++;
                    }
                } catch (IOException e) {
                    System.out.println("Помилка читання");
                }

            } else if (choice == 3) {
                System.out.print("Початковий рядок: ");
                int start = Integer.parseInt(scanner.nextLine());
                System.out.print("Кінцевий рядок: ");
                int end = Integer.parseInt(scanner.nextLine());

                try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
                    String line;
                    int num = 1;
                    while ((line = br.readLine()) != null) {
                        if (num >= start && num <= end) {
                            System.out.println(num + ": " + line);
                        }
                        num++;
                    }
                } catch (IOException e) {
                    System.out.println("Помилка читання.");
                }

            } else if (choice == 4) {
                System.out.print("Номер рядка для вставки: ");
                int insertLine = Integer.parseInt(scanner.nextLine());

                System.out.print("Текст рядка: ");
                String newLine = scanner.nextLine();

                try {
                    int size = countLines(fileName);
                    String[] lines = new String[size];
                    BufferedReader br = new BufferedReader(new FileReader(fileName));
                    for (int i = 0; i < size; i++) {
                        lines[i] = br.readLine();
                    }
                    br.close();

                    String[] newLines = new String[size + 1];
                    int j = 0;
                    for (int i = 0; i < newLines.length; i++) {
                        if (i == insertLine - 1) {
                            newLines[i] = newLine;
                        } else {
                            newLines[i] = lines[j];
                            j++;
                        }
                    }

                    BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
                    for (int i = 0; i < newLines.length; i++) {
                        bw.write(newLines[i]);
                        bw.newLine();
                    }
                    bw.close();
                    System.out.println("Рядок вставлено.");
                } catch (IOException e) {
                    System.out.println("Помилка при вставці.");
                }

            } else if (choice == 5) {
                System.out.println("До побачення!");
                break;
            } else {
                System.out.println("Невірний вибір.");
            }
        }

        scanner.close();
    }

    public static int countLines(String fileName) {
        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            while (br.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
        }
        return count;
    }
}

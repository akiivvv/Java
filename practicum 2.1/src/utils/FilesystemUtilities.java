package utils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FilesystemUtilities {

    private static int loadedSize = 0;

    public static void save(String path, LocalDateTime[] dates, String[] texts, int size) {
        try {
            PrintWriter pw = new PrintWriter(new FileWriter(path));
            for (int i = 0; i < size; i++) {
                pw.println(dates[i].format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
                pw.println(texts[i].trim());
                pw.println();
            }
            pw.close();
            System.out.println("Файл збережено!");
        } catch (IOException e) {
            System.out.println("Помилка при збереженні");
        }
    }

    public static int load(String path, LocalDateTime[] dates, String[] texts) {
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            loadedSize = 0;
            while ((line = br.readLine()) != null && loadedSize < 50) {
                if (line.trim().equals("")) continue;
                LocalDateTime d = LocalDateTime.parse(line.trim(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                StringBuilder text = new StringBuilder();
                while ((line = br.readLine()) != null && !line.trim().equals("")) {
                    text.append(line).append("\n");
                }
                dates[loadedSize] = d;
                texts[loadedSize] = text.toString();
                loadedSize++;
            }
            System.out.println("Щоденник завантажено");
        } catch (Exception e) {
            System.out.println("Помилка при відкритті файлу");
        }
        return 0;
    }

    public static int getLoadedSize() {
        return loadedSize;
    }
}



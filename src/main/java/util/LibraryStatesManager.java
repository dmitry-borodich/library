package util;

import service.LibraryServiceImpl;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LibraryStatesManager {
    private static final String FILE_PATH = "state.dat";

    public static void saveState(LibrarySystemState service) {
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_PATH), StandardOpenOption.CREATE))) {
            oos.writeObject(service);
        } catch (Exception e) {
            System.err.println("Ошибка при работе с файлом: " + e.getMessage());
        }
    }

    public static LibrarySystemState loadState() {
        LibrarySystemState service = null;
        if (Files.exists(Paths.get(FILE_PATH))) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_PATH)))) {
                service = (LibrarySystemState) ois.readObject();
            } catch (Exception e) {
                System.err.println("Файл содержит неподходящий объект");
            }
        }
        return service;
    }
}

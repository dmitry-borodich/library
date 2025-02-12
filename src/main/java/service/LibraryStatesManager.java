package service;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class LibraryStatesManager {
    private static final String FILE_PATH = "state.dat";

    public static void saveState(LibraryService service) {
        try(ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(FILE_PATH), StandardOpenOption.CREATE))) {
            oos.writeObject(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static LibraryService loadState() {
        LibraryService service = new LibraryService();
        if (Files.exists(Paths.get(FILE_PATH))) {
            try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(FILE_PATH)))) {
                service = (LibraryService) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return service;
    }
}

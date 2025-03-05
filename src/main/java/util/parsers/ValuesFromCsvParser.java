package util.parsers;

import model.Book;
import model.Library;
import model.Reader;

import java.util.UUID;

public class ValuesFromCsvParser {
    public static Library parseLibrary(String[] values) {
        return new Library(UUID.randomUUID(), values[1]);
    }

    public static Book parseBook(String[] values) {
        return new Book(UUID.randomUUID(), values[1], values[2], Integer.parseInt(values[3]), true);
    }

    public static Reader parseReader(String[] values){
        return new Reader(UUID.randomUUID(), values[1], Integer.parseInt(values[2]), values[3]);
    }
}

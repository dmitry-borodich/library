package service;

import model.Book;
import model.Library;
import model.Reader;

public class ValuesFromCsvParser {
    public static Library parseLibrary(String[] values) {
        return new Library(Integer.parseInt(values[0]), values[1]);
    }

    public static Book parseBook(String[] values) {
        return new Book(Integer.parseInt(values[0]), values[1], values[2], Integer.parseInt(values[3]));
    }

    public static Reader parseReader(String[] values){
        return new Reader(Integer.parseInt(values[0]), values[1], Integer.parseInt(values[2]), values[3]);
    }
}

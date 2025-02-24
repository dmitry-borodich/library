package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Library implements Serializable {
    private int id;
    private String address;
    private Set<Book> books;
    private List<Reader> readers;
    private List<HistoryNote> history;

    public Library(int id, String address) {
        this.id = id;
        this.address = address;
        books = new HashSet<>();
        readers = new ArrayList<>();
        history = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setBooks(List<Book> books) {
        this.books.addAll(books);
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setReaders(List<Reader> readers) {
        this.readers.addAll(readers);
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public void addHistoryNote(HistoryNote historyNote) {
        history.add(historyNote);
    }

    public List<HistoryNote> getHistory() {
        return history;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Library)) return false;
        Library library = (Library) obj;
        return id == library.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

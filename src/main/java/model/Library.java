package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Library {
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

    public int getId() { return id; }

    public String getAddress() { return address; }

}

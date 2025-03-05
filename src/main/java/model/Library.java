package model;

import java.util.*;

public class Library {
    private UUID id;
    private String address;
    private Set<Book> books;
    private List<Reader> readers;
    private List<HistoryNote> history;

    public Library(UUID id, String address) {
        this.id = id;
        this.address = address;
        books = new HashSet<>();
        readers = new ArrayList<>();
        history = new ArrayList<>();
    }

    public UUID getId() { return id; }

    public String getAddress() { return address; }

}

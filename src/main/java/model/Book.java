package model;
import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private int year;
    private boolean isInLibrary;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isInLibrary = true;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public boolean getIsInLibrary() {
        return isInLibrary;
    }

    public void setIsInLibraryTrue() {
        isInLibrary = true;
    }
    public void setIsInLibraryFalse() {
        isInLibrary = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return this.id == book.id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}

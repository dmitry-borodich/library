package model;

public class Book  {
    private int id;
    private String title;
    private String author;
    private int year;
    private boolean isInLibrary;

    public Book(int id, String title, String author, int year, boolean isInLibrary) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.isInLibrary = isInLibrary;
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

}

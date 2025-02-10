package model;
import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private int year;
    //private boolean isReturned;

    public Book(int id, String title, String author, int year) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        //this.isReturned = true;
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

    //public boolean isReturned(){return isReturned;}

    //public void returnBook() {isReturned = true;}
}

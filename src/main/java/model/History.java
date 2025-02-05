package model;
import java.time.LocalDate;
import java.io.Serializable;

public class History implements Serializable {
    private int bookId;
    private int readerId;
    private LocalDate date;
    private boolean isReturned;

    public History(int readerId, int bookId) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.date = LocalDate.now();
        this.isReturned = false;
    }

    public int getBookId() {
        return bookId;
    }

    public int getReaderId() {
        return readerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isReturned(){
        return isReturned;
    }

    public void returnBook(){isReturned = true;}
}

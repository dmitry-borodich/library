package model;
import java.time.LocalDate;

public class HistoryNote  {
    private int bookId;
    private int readerId;
    private LocalDate date;
    private boolean isReturned;

    public HistoryNote(int readerId, int bookId, LocalDate date, boolean isReturned) {
        this.bookId = bookId;
        this.readerId = readerId;
        this.date = date;
        this.isReturned = isReturned;
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

    public boolean isReturned() {
        return isReturned;
    }

}

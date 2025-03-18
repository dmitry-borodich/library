package model;
import java.time.LocalDate;
import java.util.UUID;

public class HistoryNote  {
    private UUID id;
    private UUID bookId;
    private UUID readerId;
    private LocalDate date;
    private boolean isReturned;

    public HistoryNote(UUID id, UUID readerId, UUID bookId, LocalDate date, boolean isReturned) {
        this.id = id;
        this.bookId = bookId;
        this.readerId = readerId;
        this.date = date;
        this.isReturned = isReturned;
    }

    public UUID getBookId() {
        return bookId;
    }

    public UUID getReaderId() {
        return readerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isReturned() {
        return isReturned;
    }

}

package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "history")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistoryNote  {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "book_id", nullable = false)
    private UUID bookId;

    @Column(name = "reader_id", nullable = false)
    private UUID readerId;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "is_returned")
    private boolean isReturned;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", nullable = false)
    @JsonIgnore
    private Library library;
}

package model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book  {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "year")
    private int year;

    @Column(name = "is_in_library", nullable = false)
    private boolean isInLibrary;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "library_id", nullable = false)
    @JsonIgnore
    private Library library;

    public boolean getIsInLibrary() {
        return isInLibrary;
    }
}

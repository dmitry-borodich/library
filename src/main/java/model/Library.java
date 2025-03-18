package model;

import java.util.*;
        import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "libraries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Library {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Book> books;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reader> readers;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HistoryNote> history;
}

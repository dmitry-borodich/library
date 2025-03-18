package repository;

import java.util.List;
import model.HistoryNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryNote, UUID> {
    List<HistoryNote> findByReaderIdAndLibraryId(UUID readerId, UUID libraryId);
    List<HistoryNote> findByBookIdAndReaderIdAndLibraryId(UUID bookId, UUID readerId, UUID libraryId);
}

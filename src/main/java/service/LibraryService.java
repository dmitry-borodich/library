package service;
import model.Book;
import model.Library;
import model.Reader;
import model.HistoryNote;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface LibraryService {
    void loadLibraries(String filePath);
    List<HistoryNote> getHistory(UUID readerId, UUID libraryId, boolean displayHistory) ;
    List<Library> getLibraries(boolean displayHistory) ;
}

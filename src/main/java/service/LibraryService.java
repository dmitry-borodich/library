package service;
import model.Book;
import model.Library;
import model.Reader;
import model.HistoryNote;
import java.util.List;
import java.util.Set;

public interface LibraryService {
    void loadLibraries(String filePath);
    List<HistoryNote> getHistory(int readerId, int libraryId, boolean displayHistory) ;
    Library getLibrary(int id);
}

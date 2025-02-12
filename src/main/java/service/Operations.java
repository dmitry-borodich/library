package service;
import exceptions.LibraryNotFoundException;
import model.Book;
import model.Reader;
import model.HistoryNote;
import java.util.List;
import java.util.Set;

public interface Operations {
    void loadLibraries(String filePath);
    void loadBooks(String filePath, int libraryId) throws LibraryNotFoundException;
    void loadReaders (String filePath, int libraryId) throws LibraryNotFoundException;
    void lendBook(int readerId, int bookId, int libraryId) throws LibraryNotFoundException;
    void returnBook(int readerId, int bookId, int libraryId) throws LibraryNotFoundException;
    Set<Book> getBooks(int libraryId, boolean displayBooks) throws LibraryNotFoundException;
    List<Reader> getReaders(int libraryId) throws LibraryNotFoundException;
    List<HistoryNote> getHistory(int readerId, int libraryId, boolean displayHistory) throws LibraryNotFoundException;
}

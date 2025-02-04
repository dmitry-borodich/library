package service;
import model.Book;
import model.Reader;
import model.History;
import java.util.List;

public interface Operations {
    void loadBooks(String filePath);
    void loadReaders(String filePath);
    void lendBook(int readerId, int bookId);
    void returnBook(int readerId, int bookId);
    List<Book> getBooks();
    List<Reader> getReaders();
    List<History> getHistory(int readerId);
}

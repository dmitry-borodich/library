package service;

import model.Book;

import java.util.List;
import java.util.UUID;

public interface BookService {
    void loadBooks(String filePath, UUID libraryId) ;
    void lendBook(UUID readerId, UUID bookId, UUID libraryId) ;
    void returnBook(UUID readerId, UUID bookId, UUID libraryId) ;
    List<Book> getBooks(UUID libraryId, boolean displayBooks) ;
}

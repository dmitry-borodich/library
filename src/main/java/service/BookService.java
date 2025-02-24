package service;

import model.Book;

import java.util.Set;

public interface BookService {
    void loadBooks(String filePath, int libraryId) ;
    void lendBook(int readerId, int bookId, int libraryId) ;
    void returnBook(int readerId, int bookId, int libraryId) ;
    Set<Book> getBooks(int libraryId, boolean displayBooks) ;
}

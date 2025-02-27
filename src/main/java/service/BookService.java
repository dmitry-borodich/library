package service;

import model.Book;

import java.util.List;

public interface BookService {
    void loadBooks(String filePath, int libraryId) ;
    void lendBook(int readerId, int bookId, int libraryId) ;
    void returnBook(int readerId, int bookId, int libraryId) ;
    List<Book> getBooks(int libraryId, boolean displayBooks) ;
}

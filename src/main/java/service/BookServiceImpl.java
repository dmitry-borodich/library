package service;

import exceptions.LibraryNotFoundException;
import model.Book;
import model.HistoryNote;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class BookServiceImpl implements BookService, Serializable {
    private LibraryService libraryService;

    public BookServiceImpl(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void loadBooks(String filePath, int libraryId)  {
        FileLoader<Book> bookFileLoader = new FileLoader<>(ValuesFromCsvParser::parseBook);
        List<Book> books = bookFileLoader.load(filePath);
        libraryService.getLibrary(libraryId).setBooks(books);
    }

    @Override
    public void lendBook(int readerId, int bookId, int libraryId) {
        getBooks(libraryId, false).stream()
                .filter(book -> book.getId() == bookId)
                .findFirst()
                .ifPresentOrElse (book -> {
                    if(!book.getIsInLibrary()){
                        System.out.println("Книга уже кем-то взята");
                    }
                    else {
                        book.setIsInLibrary(false);
                        System.out.println("Книга выдана");
                        libraryService.getLibrary(libraryId).addHistoryNote(new HistoryNote(readerId, bookId));
                    }
                }, () -> System.err.println("Книга не найдена"));
    }

    @Override
    public void returnBook(int readerId, int bookId, int libraryId) {
        List<HistoryNote> history = libraryService.getHistory(readerId, libraryId, false);

        history.stream()
                .filter(operation -> operation.getBookId() == bookId
                        && operation.getReaderId() == readerId
                        && !operation.isReturned())
                .findFirst()
                .ifPresent(operation -> {
                    operation.returnBook();
                    try {
                        Book book = bookExists(bookId, libraryId);
                        if (book != null){
                            book.setIsInLibrary(true);
                            System.out.println("Книга возвращена");
                        }
                        else {
                            System.err.println("Книга не найдена");
                        }
                    } catch (LibraryNotFoundException e) {
                        System.err.println("Библиотека с номером " + libraryId + " не найдена");
                    }
                });
    }

    @Override
    public Set<Book> getBooks(int libraryId, boolean displayBooks) {
        Set<Book> books = libraryService.getLibrary(libraryId).getBooks();
        if (displayBooks) {
            System.out.println("Books: ");
            books.forEach(book ->  System.out.println("Id: " + book.getId() + ", title: " + book.getTitle() + ", author: " + book.getAuthor() + ", year: " + book.getYear() + ", isInLibrary: " + book.getIsInLibrary()));
            }
        return books;
    }

    public Book bookExists(int bookId, int libraryId) {
        return getBooks(libraryId, false).stream()
                .filter(book -> book.getId() == bookId)
                .findFirst()
                .orElse(null);
    }
}

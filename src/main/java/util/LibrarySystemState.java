package util;

import service.BookService;
import service.LibraryService;
import service.ReaderService;

import java.io.Serializable;

public class LibrarySystemState implements Serializable {
    private LibraryService libraryService;
    private BookService bookService;
    private ReaderService readerService;

    public LibrarySystemState(LibraryService libraryService, BookService bookService, ReaderService readerService) {
        this.libraryService = libraryService;
        this.bookService = bookService;
        this.readerService = readerService;
    }
    public LibraryService getLibraryService() {
        return libraryService;
    }

    public BookService getBookService() {
        return bookService;
    }

    public ReaderService getReaderService() {
        return readerService;
    }
}

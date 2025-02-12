package service;
import exceptions.LibraryNotFoundException;
import model.Book;
import model.HistoryNote;
import model.Library;
import model.Reader;

import java.io.*;
import java.util.*;

public class LibraryService implements Operations, Serializable {
    private List<Library> libraries = new ArrayList<>();

    @Override
    public void loadLibraries(String filePath){
        FileLoader<Library> libraryFileLoader = new FileLoader<>(ValuesFromCsvParser::parseLibrary);
        libraries = libraryFileLoader.load(filePath);
    }
    @Override
    public void loadBooks(String filePath, int libraryId) throws LibraryNotFoundException {
        FileLoader<Book> bookFileLoader = new FileLoader<>(ValuesFromCsvParser::parseBook);
        List<Book> books = bookFileLoader.load(filePath);
        getLibrary(libraryId).setBooks(books);
    }

    @Override
    public void loadReaders(String filePath, int libraryId) throws LibraryNotFoundException {
        FileLoader<Reader> readerFileLoader = new FileLoader<>(ValuesFromCsvParser::parseReader);
        List<Reader> readers = readerFileLoader.load(filePath);
        getLibrary(libraryId).setReaders(readers);
    }

    @Override
    public void lendBook(int readerId, int bookId, int libraryId) throws LibraryNotFoundException {
        for (Book book : getBooks(libraryId, false)) {
            if (book.getId() == bookId && !book.getIsInLibrary() ) {
                System.out.println("Книга уже кем-то взята");
                return;
            }
            else if (book.getId() == readerId && book.getIsInLibrary() ) {
                book.setIsInLibraryFalse();
                System.out.println("Книга выдана");
                getLibrary(libraryId).addHistoryNote(new HistoryNote(readerId, bookId));
            }
        }
    }

    @Override
    public void returnBook(int readerId, int bookId, int libraryId) throws LibraryNotFoundException {
        List<HistoryNote> history = getHistory(readerId, libraryId, false);

        history.stream()
                .filter(operation -> operation.getBookId() == bookId
                        && operation.getReaderId() == readerId
                        && !operation.isReturned())
                .findFirst()
                .ifPresent(operation -> {
                    operation.returnBook();
                    try {
                        getBooks(libraryId, false).stream().filter(book -> book.getId() == bookId)
                                .findFirst()
                                .ifPresent(book -> {
                                    book.setIsInLibraryTrue();
                                    System.out.println("Книга возвращена");
                                });
                    } catch (LibraryNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override
    public Set<Book> getBooks(int libraryId, boolean displayBooks) throws LibraryNotFoundException {
        Set<Book> books = getLibrary(libraryId).getBooks();
        if (displayBooks) {
            System.out.println("Books: ");
            for (Book book : books) {
                System.out.println("Id: " + book.getId() + ", title: " + book.getTitle() + ", author: " + book.getAuthor() + ", year: " + book.getYear() + ", isInLibrary: " + book.getIsInLibrary());
            }
        }
        return books;
    }

    @Override
    public List<Reader> getReaders(int libraryId) throws LibraryNotFoundException {
        List<Reader> readers = getLibrary(libraryId).getReaders();
        System.out.println("Readers: ");
        for (Reader reader : readers) {
            System.out.println("Id: " + reader.getId() + ", name: " + reader.getName() +", age: " + reader.getAge() + ", passport number: " + reader.getPassportNumber());
        }
        return readers;
    }

    @Override
    public List<HistoryNote> getHistory(int readerId, int libraryId, boolean displayHistory) throws LibraryNotFoundException {
        List<HistoryNote> history = getLibrary(libraryId).getHistory();
        System.out.println("History: ");
        for (HistoryNote operation : history) {
            if (operation.getReaderId() == readerId) {
                System.out.println("BookId: " + operation.getBookId() + ", date: " + operation.getDate() + (operation.isReturned() ? ", Returned" : ""));
            }
        }
        return history;
    }
    public void saveState() {
        LibraryStatesManager.saveState(this);
    }

    public Library getLibrary(int id) throws LibraryNotFoundException {
        for (Library library : libraries) {
            if (library.getId() == id) {
                return library;
            }
        }
        throw new LibraryNotFoundException("Библиотека с номером " + id + " не найдена");
    }
}

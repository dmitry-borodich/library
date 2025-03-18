package service;

import exceptions.DatabaseServiceException;
import lombok.RequiredArgsConstructor;
import model.Book;
import model.HistoryNote;
import model.Library;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import repository.BookRepository;
import repository.HistoryRepository;
import repository.LibraryRepository;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@ComponentScan("repository")
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final LibraryRepository libraryRepository;
    private final HistoryRepository historyRepository;

    @Override
    public void loadBooks(String filePath, UUID libraryId)  {
        FileLoader<Book> bookFileLoader = new FileLoader<>(ValuesFromCsvParser::parseBook);
        List<Book> books = bookFileLoader.load(filePath);

        Library library = libraryRepository.findById(libraryId).orElseThrow(() ->
                new DatabaseServiceException("Библиотека с таким id не найдена"));
        books.forEach(book -> {
            book.setLibrary(library);
            bookRepository.save(book);
        });
    }

    @Override
    public void lendBook(UUID readerId, UUID bookId, UUID libraryId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new DatabaseServiceException("Книга с таким номером не найдена"));
        Library library = libraryRepository.findById(libraryId).orElseThrow(() ->
                new DatabaseServiceException("Библиотека с таким id не найдена"));
        if(book.getIsInLibrary()) {
            book.setInLibrary(false);
            bookRepository.save(book);

            HistoryNote historyNote = new HistoryNote(UUID.randomUUID(), bookId, readerId, LocalDate.now(), false, library);
            historyRepository.save(historyNote);
        }
        else {
            System.out.println("Книга отсутствует в библиотеке");
        }
    }

    @Override
    public void returnBook(UUID readerId, UUID bookId, UUID libraryId) {
        List<HistoryNote> historyNotes = historyRepository.findByBookIdAndReaderIdAndLibraryId(bookId, readerId, libraryId);
        HistoryNote historyNote = historyNotes.stream()
                .filter(note -> !note.isReturned())
                .findFirst()
                .orElseThrow(() ->
                        new DatabaseServiceException("Запись о взятии не найдена"));
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new DatabaseServiceException("Книга с таким номером не найдена"));
        book.setInLibrary(true);
        bookRepository.save(book);

        historyNote.setReturned(true);
        historyRepository.save(historyNote);
    }

    @Override
    public List<Book> getBooks(UUID libraryId, boolean displayBooks) {
        List<Book> books = bookRepository.findByLibraryId(libraryId);
        if (displayBooks) {
            System.out.println("Books: ");
            books.forEach(book -> System.out.println("Id: " + book.getId() + ", title: " + book.getTitle() + ", author: " + book.getAuthor() + ", year: " + book.getYear() + ", isInLibrary: " + book.getIsInLibrary()));
        }
        return books;
    }
}

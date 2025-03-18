package main;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.BookService;
import service.ReaderService;
import service.LibraryService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Adapter {

   private final LibraryService libraryService;
   private final BookService bookService;
   private final ReaderService readerService;


   @PostMapping("/f/{type}")
   public ResponseEntity<?> readFile(@PathVariable String type, @RequestParam String filePath, @RequestParam(required = false) UUID libraryId) {
       switch (type) {
           case "library":
               libraryService.loadLibraries(filePath);
               break;
           case "book":
               bookService.loadBooks(filePath, libraryId);
               break;
           case "reader":
               readerService.loadReaders(filePath, libraryId);
               break;
           default:
               throw new IllegalArgumentException("Неизвестный тип для записи из файла: " + type);
       }
       return ResponseEntity.ok("Объекты загружены из файла");
   }

   @PostMapping("/lend")
   public ResponseEntity<?> lendBook(@RequestParam UUID readerId, @RequestParam UUID bookId, @RequestParam UUID libraryId) {
       bookService.lendBook(readerId, bookId, libraryId);
       return ResponseEntity.ok("Книга " + bookId + " выдана чиатателю " + readerId);
   }

    @PostMapping("/return")
    public ResponseEntity<?> returnBook(@RequestParam UUID readerId, @RequestParam UUID bookId, @RequestParam UUID libraryId) {
        bookService.returnBook(readerId, bookId, libraryId);
        return ResponseEntity.ok("Книга " + bookId + " возвращена в библиотеку");
    }

    @PostMapping("/print/{type}")
    public ResponseEntity<?> printObject(@PathVariable String type, @RequestParam(required = false) UUID libraryId, @RequestParam(required = false) UUID readerId) {
        switch (type) {
            case "books":
                return ResponseEntity.ok(bookService.getBooks(libraryId, true));
            case "readers":
                return ResponseEntity.ok(readerService.getReaders(libraryId));
            case "libraries":
                return ResponseEntity.ok(libraryService.getLibraries(true));
            case "borrowed":
                return ResponseEntity.ok(libraryService.getHistory(readerId, libraryId, true));
            default:
                throw new IllegalArgumentException("Неизвестный тип для вывода: " + type);
        }
    }
}

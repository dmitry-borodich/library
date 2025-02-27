package main;

import service.BookService;
import service.ReaderService;
import util.parsers.ParsedArguments;
import service.LibraryService;

public class Adapter {
   private LibraryService libraryService;
   private BookService bookService;
   private ReaderService readerService;

   public Adapter(LibraryService libraryService, BookService bookService, ReaderService readerService) {
       this.libraryService = libraryService;
       this.bookService = bookService;
       this.readerService = readerService;
   }

   public void execute (ParsedArguments parsedArguments)  {
       switch(parsedArguments.getCommand()){
           case LOADLIBRARIES:
               libraryService.loadLibraries(parsedArguments.getFilePath());
               break;
           case READFILE:
               handleReadFile(parsedArguments);
               break;
           case LENDBOOK:
               bookService.lendBook(parsedArguments.getReaderId(), parsedArguments.getBookId(), parsedArguments.getLibraryId());
               break;
           case RETURNBOOK:
               bookService.returnBook(parsedArguments.getReaderId(), parsedArguments.getBookId(), parsedArguments.getLibraryId());
               break;
           case PRINTOBJECT:
               handlePrintObject(parsedArguments);
               break;
       }
   }

    private void handleReadFile(ParsedArguments parsedArguments)  {
        switch (parsedArguments.getType()) {
            case "book":
                bookService.loadBooks(parsedArguments.getFilePath(), parsedArguments.getLibraryId());
                break;
            case "reader":
                readerService.loadReaders(parsedArguments.getFilePath(), parsedArguments.getLibraryId());
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип для записи из файла: " + parsedArguments.getType());
        }
    }

    private void handlePrintObject(ParsedArguments parsedArguments)  {
       switch(parsedArguments.getType()){
           case "books":
               bookService.getBooks(parsedArguments.getLibraryId(), true);
               break;
           case "readers":
               readerService.getReaders(parsedArguments.getLibraryId());
               break;
           case "libraries":
               libraryService.getLibraries(true);
               break;
           case "borrowed":
               libraryService.getHistory(parsedArguments.getReaderId(), parsedArguments.getLibraryId(), true);
               break;
           default:
               throw new IllegalArgumentException("Неизвестный тип для вывода: " + parsedArguments.getType());
        }
    }
}

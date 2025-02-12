package main;

import exceptions.LibraryNotFoundException;
import parser.ParsedArguments;
import service.Operations;

public class Adapter {
   private Operations libraryService;

   public Adapter(Operations libraryService) {
       this.libraryService = libraryService;
   }

   public void execute (ParsedArguments parsedArguments) throws LibraryNotFoundException {
       switch(parsedArguments.getCommand()){
           case LOADLIBRARIES:
               libraryService.loadLibraries(parsedArguments.getFilePath());
               break;
           case READFILE:
               handleReadFile(parsedArguments);
               break;
           case LENDBOOK:
               libraryService.lendBook(parsedArguments.getReaderId(), parsedArguments.getBookId(), parsedArguments.getLibraryId());
               break;
           case RETURNBOOK:
               libraryService.returnBook(parsedArguments.getReaderId(), parsedArguments.getBookId(), parsedArguments.getLibraryId());
               break;
           case PRINTOBJECT:
               handlePrintObject(parsedArguments);
               break;
       }
   }

    private void handleReadFile(ParsedArguments parsedArguments) throws LibraryNotFoundException {
        switch (parsedArguments.getType()) {
            case "book":
                libraryService.loadBooks(parsedArguments.getFilePath(), parsedArguments.getLibraryId());
                break;
            case "reader":
                try {
                    libraryService.loadReaders(parsedArguments.getFilePath(), parsedArguments.getLibraryId());
                } catch (exceptions.LibraryNotFoundException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип для записи из файла: " + parsedArguments.getType());
        }
    }

    private void handlePrintObject(ParsedArguments parsedArguments) throws LibraryNotFoundException {
       switch(parsedArguments.getType()){
           case "books":
               libraryService.getBooks(parsedArguments.getLibraryId(), true);
               break;
           case "reader":
               libraryService.getReaders(parsedArguments.getLibraryId());
               break;
           case "borrowed":
               libraryService.getHistory(parsedArguments.getReaderId(), parsedArguments.getLibraryId(), true);
               break;
           default:
               throw new IllegalArgumentException("Неизвестный тип для вывода: " + parsedArguments.getType());
        }
    }
}

package main;

import Parser.ParsedArguments;
import service.Operations;

public class Adapter {
   private Operations libraryService;

   public Adapter(Operations libraryService) {
       this.libraryService = libraryService;
   }

   public void execute (ParsedArguments parsedArguments) {
       switch(parsedArguments.getCommand()){
           case READFILE:
               handleReadFile(parsedArguments);
               break;
           case LENDBOOK:
               libraryService.lendBook(parsedArguments.getReaderId(), parsedArguments.getBookId());
               break;
           case RETURNBOOK:
               libraryService.returnBook(parsedArguments.getReaderId(), parsedArguments.getBookId());
               break;
           case PRINTOBJECT:
               handlePrintObject(parsedArguments);
               break;
       }
   }

    private void handleReadFile(ParsedArguments parsedArguments) {
        switch (parsedArguments.getType()) {
            case "book":
                libraryService.loadBooks(parsedArguments.getFilePath());
                break;
            case "reader":
                libraryService.loadReaders(parsedArguments.getFilePath());
                break;
            default:
                throw new IllegalArgumentException("Неизвестный тип для записи из файла: " + parsedArguments.getType());
        }
    }

    private void handlePrintObject(ParsedArguments parsedArguments) {
       switch(parsedArguments.getType()){
           case "books":
               libraryService.getBooks();
               break;
           case "reader":
               libraryService.getReaders();
               break;
           case "borrowed":
               libraryService.getHistory(parsedArguments.getReaderId());
               break;
           default:
               throw new IllegalArgumentException("Неизвестный тип для вывода: " + parsedArguments.getType());
        }
    }
}

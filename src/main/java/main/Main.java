package main;

import service.LibraryService;

public class Main {
    public static void main(String[] args) {
        LibraryService libraryService = LibraryService.loadState();
        switch(args[0]){
            case "-f":
                if (args[1].equals("book")) {
                    libraryService.loadBooks(args[2]);
                }
                else if (args[1].equals("reader")) {
                    libraryService.loadReaders(args[2]);
                }
                break;
            case "-lend":
                libraryService.lendBook(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case "-return":
                libraryService.returnBook(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case "-list":
                if(args[1].equals("books")) {
                    libraryService.getBooks();
                }
                else if(args[1].equals("readers")) {
                    libraryService.getReaders();
                }
                else if(args[1].equals("borrowed")) {
                    libraryService.getHistory(Integer.parseInt(args[2]));
                }
                break;
        }
        libraryService.saveState();
    }
}
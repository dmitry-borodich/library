package main;

import service.*;
import util.LibraryStatesManager;
import util.LibrarySystemState;

public class LibraryApp {
    private LibrarySystemState systemState;

    public Adapter initialize(){
         systemState = LibraryStatesManager.loadState();
        if(systemState == null){
            LibraryServiceImpl libraryService = new LibraryServiceImpl();
            BookServiceImpl bookService = new BookServiceImpl(libraryService);
            ReaderServiceImpl readerService = new ReaderServiceImpl(libraryService);
            systemState = new LibrarySystemState(libraryService, bookService, readerService);
        }
        return new Adapter(systemState.getLibraryService(), systemState.getBookService(), systemState.getReaderService());
    }

    public void saveState(){
        LibraryStatesManager.saveState(systemState);
    }
}

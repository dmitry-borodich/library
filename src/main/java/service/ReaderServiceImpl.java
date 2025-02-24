package service;

import model.Reader;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.io.Serializable;
import java.util.List;

public class ReaderServiceImpl implements ReaderService, Serializable {
    private LibraryService libraryService;

    public ReaderServiceImpl(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void loadReaders(String filePath, int libraryId)  {
        FileLoader<Reader> readerFileLoader = new FileLoader<>(ValuesFromCsvParser::parseReader);
        List<Reader> readers = readerFileLoader.load(filePath);
        libraryService.getLibrary(libraryId).setReaders(readers);
    }

    @Override
    public List<Reader> getReaders(int libraryId)  {
        List<Reader> readers = libraryService.getLibrary(libraryId).getReaders();
        System.out.println("Readers: ");
            readers.forEach(reader -> System.out.println("Id: " + reader.getId() + ", name: " + reader.getName() +", age: " + reader.getAge() + ", passport number: " + reader.getPassportNumber()));
        return readers;
    }
}

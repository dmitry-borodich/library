package service;

import exceptions.DatabaseServiceException;
import lombok.RequiredArgsConstructor;
import model.Library;
import model.Reader;
import org.springframework.stereotype.Service;
import repository.LibraryRepository;
import repository.ReaderRepository;
import util.FileLoader;
import util.parsers.ValuesFromCsvParser;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl implements ReaderService{

    private final ReaderRepository readerRepository;
    private final LibraryRepository libraryRepository;

    @Override
    public void loadReaders(String filePath, UUID libraryId)  {
        FileLoader<Reader> readerFileLoader = new FileLoader<>(ValuesFromCsvParser::parseReader);
        List<Reader> readers = readerFileLoader.load(filePath);
        Library library = libraryRepository.findById(libraryId).orElseThrow(() ->
                new DatabaseServiceException("Библиотека с таким id не найдена"));
        readers.forEach(reader -> {
            reader.setLibrary(library);
            readerRepository.save(reader);
        });
    }

    @Override
    public List<Reader> getReaders(UUID libraryId)  {
        List<Reader> readers = readerRepository.findByLibraryId(libraryId);
        System.out.println("Readers: ");
        readers.forEach(reader -> System.out.println("Id: " + reader.getId() + ", name: " + reader.getName() +", age: " + reader.getAge() + ", passport number: " + reader.getPassportNumber()));
        return readers;
    }
}

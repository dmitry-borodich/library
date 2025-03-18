package service;

import model.Reader;

import java.util.List;
import java.util.UUID;

public interface ReaderService {
    void loadReaders (String filePath, UUID libraryId) ;
    List<Reader> getReaders(UUID libraryId);
}

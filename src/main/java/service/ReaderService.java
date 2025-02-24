package service;

import model.Reader;

import java.util.List;

public interface ReaderService {
    void loadReaders (String filePath, int libraryId) ;
    List<Reader> getReaders(int libraryId) ;
}

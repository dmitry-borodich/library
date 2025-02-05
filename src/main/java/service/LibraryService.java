package service;
import model.Book;
import model.Reader;
import model.History;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import com.opencsv.CSVReader;

public class LibraryService implements Operations, Serializable {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private List<History> history = new ArrayList<>();

    @Override
    public void loadBooks(String filePath) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            books.clear();
            String[] values;
            csvReader.readNext();

            while ((values = csvReader.readNext()) != null) {
                int id = Integer.parseInt(values[0]);
                String title = values[1];
                String author = values[2];
                int year = Integer.parseInt(values[3]);
                books.add(new Book(id, title, author, year));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadReaders(String filePath) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            readers.clear();
            String[] values;
            csvReader.readNext();

            while ((values = csvReader.readNext()) != null) {
                int id = Integer.parseInt(values[0]);
                String name = values[1];
                int age = Integer.parseInt(values[2]);
                String passportNumber = values[3];
                readers.add(new Reader(id, name, age, passportNumber));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void lendBook(int readerId, int bookId) {
        for (History operation : history) {
            if (operation.getBookId() == bookId && !operation.isReturned() ) {
                System.out.println("Книга уже взята");
                return;
            }
        }
        System.out.println("Книга выдана");
        history.add(new History(readerId, bookId));
    }

    @Override
    public void returnBook(int readerId, int bookId) {
        for (History operation : history) {
            if (operation.getBookId() == bookId && operation.getReaderId() == readerId && !operation.isReturned()) {
                operation.returnBook();
                System.out.println("Книга возвращена");
            }
        }
    }

    @Override
    public List<Book> getBooks() {
        System.out.println("Books: ");
        for (Book book : books) {
            System.out.println("Id: " + book.getId() + ", title: " + book.getTitle() +", author: :" + book.getAuthor() + ", year: " + book.getYear());
        }
        return books;
    }

    @Override
    public List<Reader> getReaders() {
        System.out.println("Readers: ");
        for (Reader reader : readers) {
            System.out.println("Id: " + reader.getId() + ", name: " + reader.getName() +", age:" + reader.getAge() + ", passport number: " + reader.getPassportNumber());
        }
        return readers;
    }

    @Override
    public List<History> getHistory(int readerId) {
        System.out.println("History: ");
        for (History operation : history) {
            if (operation.getReaderId() == readerId) {
                System.out.println("BookId: " + operation.getBookId() + ", date: " + operation.getDate() + ", is returned: " + operation.isReturned());
            }
        }
        return history;
    }

    public void saveState() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream("state.csv"))) {
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static LibraryService loadState() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream("state.csv"))){
            return (LibraryService) objectInputStream.readObject();
        }
        catch (Exception e) {
            return new LibraryService();
        }
    }
}

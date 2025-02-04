package service;
import model.Book;
import model.Reader;
import model.History;
import java.util.List;
import java.util.ArrayList;
import com.opencsv.CSVReader;
import java.io.FileReader;

public class LibraryService implements Operations {
    private List<Book> books = new ArrayList<>();
    private List<Reader> readers = new ArrayList<>();
    private List<History> history = new ArrayList<>();

    @Override
    public void loadBooks(String filePath) {
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
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
            String[] values;
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
    public void lendBook(int bookId, int readerId) {
        for (History operation : history) {
            if (operation.getBookId() == bookId && !operation.isReturned() ) {
                System.out.println("Книга уже взята");
                return;
            }
        }
        System.out.println("Книга выдана");
        history.add(new History(bookId, readerId));
    }

    @Override
    public void returnBook(int bookId, int readerId) {
        for (History operation : history) {
            if (operation.getBookId() == bookId && operation.getReaderId() == readerId && !operation.isReturned()) {
                operation.returnBook();
                System.out.println("Книга возвращена");
            }
            else {
                System.out.println("Запись о выдаче книги не найдена");
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
            System.out.println("Id: " + reader.getId() + ", name: " + reader.getName() +", age: :" + reader.getAge() + ", passport number: " + reader.getPassportNumber());
        }
        return readers;
    }

    @Override
    public List<History> getHistory(int readerId) {
        for (History operation : history) {
            System.out.println("BookId: " + operation.getBookId() +", date: :" + operation.getDate() + ", is returned: " + operation.isReturned());
        }
        return history;
    }

}

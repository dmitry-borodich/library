package mapper;

import model.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BookMapper implements Mapper<Book> {

    @Override
    public Book map(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        int year = rs.getInt("year");
        boolean is_in_library = rs.getBoolean("is_in_library");
        return new Book(id, title, author, year, is_in_library);
    }
}

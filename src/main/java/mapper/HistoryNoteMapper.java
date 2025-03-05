package mapper;

import model.HistoryNote;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class HistoryNoteMapper implements Mapper<HistoryNote> {

    @Override
    public HistoryNote map (ResultSet rs) throws SQLException{
        UUID id = (UUID) rs.getObject("id");
        UUID book_id = (UUID) rs.getObject("book_id");
        UUID reader_id = (UUID) rs.getObject("reader_id");
        LocalDate date = rs.getDate("date").toLocalDate();
        boolean is_returned = rs.getBoolean("is_returned");
        return new HistoryNote(id, book_id, reader_id, date, is_returned);
    }
}

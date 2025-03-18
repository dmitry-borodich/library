package mapper;

import model.Reader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class ReaderMapper implements Mapper<Reader> {

    @Override
    public Reader map (ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String name = rs.getString("name");
        int age = rs.getInt("age");
        String passport_number = rs.getString("passport_number");
        return new Reader(id, name, age, passport_number);
    }
}

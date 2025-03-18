package mapper;

import model.Library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class LibraryMapper implements Mapper<Library> {

    @Override
    public Library map (ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        String address = rs.getString("address");
        return new Library(id, address);
    }
}
